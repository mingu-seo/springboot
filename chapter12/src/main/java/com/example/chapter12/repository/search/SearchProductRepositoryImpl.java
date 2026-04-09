package com.example.chapter12.repository.search;

import com.example.chapter12.entity.Product;
import com.example.chapter12.entity.QProduct;
import com.example.chapter12.entity.QReview;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchProductRepositoryImpl extends QuerydslRepositorySupport implements SearchProductRepository {

    public SearchProductRepositoryImpl() {
        super(Product.class);
    }

    @Override
    public void search() {
        log.info("search()");

        QProduct qProduct = QProduct.product;
        JPQLQuery<Product> jpqlQuery = from(qProduct);
        jpqlQuery.select(qProduct).where(qProduct.idx.eq(10L));
        log.info(jpqlQuery);

        List<Product> result = jpqlQuery.fetch();
        result.forEach(p-> System.out.println(p));
    }

    @Override
    public void search2() {
        QProduct qProduct = QProduct.product;
        QReview qReview = QReview.review;

        JPQLQuery<Product> jpqlQuery = from(qProduct);
        jpqlQuery.leftJoin(qReview).on(qReview.product.eq(qProduct));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(qProduct, qReview.count(), qReview.rating.avg()).groupBy(qProduct);
        List<Tuple> result = tuple.fetch();
        System.out.println(result);
    }

    @Override
    public Page<Object[]> search3(String type, String word, Pageable pageable) {
        QProduct qProduct = QProduct.product;
        QReview qReview = QReview.review;

        JPQLQuery<Product> jpqlQuery = from(qProduct);
        jpqlQuery.leftJoin(qReview).on(qReview.product.eq(qProduct));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(qProduct, qReview.count(), qReview.rating.avg());

        // 동적조건 처리
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qProduct.idx.gt(0L);
        builder.and(expression);

        // type 검색타입 (pname : 상품명, rcontent : 리뷰, all : 둘다)
        if (type != null) {
            BooleanBuilder conBuilder = new BooleanBuilder();
            if (type.equals("pname")) {
                conBuilder.and(qProduct.name.contains(word));
            } else if (type.equals("rcontent")) {
                conBuilder.and(qReview.content.contains(word));
            } else {
                conBuilder.or(qProduct.name.contains(word));
                conBuilder.or(qReview.content.contains(word));
            }
            builder.and(conBuilder);
        }
        tuple.where(builder);
        tuple.groupBy(qProduct);

        // 페이징처리
        this.getQuerydsl().applyPagination(pageable, tuple);

        List<Tuple> result = tuple.fetch();
        System.out.println(result);

        // 개수
        long count = tuple.fetchCount();
        System.out.println("count:"+count);
        // Page<Object[]>로 변환
        Page page = new PageImpl<Object[]>(
                result.stream().map(Tuple::toArray).collect(Collectors.toList()),
                pageable,
                count
        );
        return page;
    }
}
