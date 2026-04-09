package com.example.chapter15.repository;

import com.example.chapter15.entity.Product;
import com.example.chapter15.entity.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void insertDummyData() {
        for (int i = 1; i <= 100; i++) {
            Product product = Product.builder()
                    .name("상품" + i)
                    .price(1000 + (i * 100))
                    .build();
            productRepository.save(product);
        }
    }

    @Test
    void findByIdTest() {
        Optional<Product> product = productRepository.findById(1L);
        if (product.isPresent()) {
            System.out.println(product.get());
        }
    }

    @Test
    void findAllTest() {
        List<Product> products = productRepository.findAll();
        products.forEach(p-> System.out.println(p));
    }

    @Test
    void updateTest() {
        Optional<Product> product = productRepository.findById(1L);
        if (product.isPresent()) {
            Product p = product.get();
            p.setName("수정된 상품");
            p.setPrice(99999);
            productRepository.save(p);
        }
    }

    @Test
    void deleteByIdTest() {
        productRepository.deleteById(2L);
    }

    // 1페이지, 10개
    @Test
    void testPageDefault() {
        Pageable pageable = PageRequest.of(0, 10);
        // Pageable 객체를 매개변수로 받는 경우 리턴타입 Page
        Page<Product> products = productRepository.findAll(pageable);
        products.forEach(p-> System.out.println(p));
        // Pageable 객체의 쿼리 결과관련 메서드
        System.out.println("총 페이지 수: " + products.getTotalPages());
        System.out.println("총 데이터 수: " + products.getTotalElements());
        System.out.println("현재 페이지 번호: " + products.getNumber());
        System.out.println("페이지당 데이터 수: " + products.getSize());
        System.out.println("다음 페이지 존재 여부: " + products.hasNext());
        System.out.println("이전 페이지 존재 여부: " + products.hasPrevious());
        System.out.println("첫 번째 페이지 여부: " + products.isFirst());
        System.out.println("마지막 페이지 여부: " + products.isLast());
    }

    @Test
    void testPageSort() {
        Pageable pageable = PageRequest.of(0, 10,
                Sort.Direction.DESC, "crdt");
        Page<Product> products = productRepository.findAll(pageable);
        products.forEach(p-> System.out.println(p));

    }

    @Test
    void testPageSort2() {
        Sort sort = Sort.by(
            Sort.Order.desc("crdt"),
            Sort.Order.asc("name")        // 복수 정렬 조건
        );
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<Product> products = productRepository.findAll(pageable);
        products.forEach(p-> System.out.println(p));
    }

    @Test
    void testQueryMethod() {
        List<Product> products = productRepository.findByPriceBetweenOrderByCrdtDesc(2000L,3000L);
        products.forEach(p-> System.out.println(p));
    }
    @Test
    void testQueryMethod2() {
        Pageable pageable = PageRequest.of(0, 10,
                Sort.Direction.DESC, "crdt");
        Page<Product> products = productRepository.findByPriceBetween(2000L,3000L,pageable);
        products.forEach(p-> System.out.println(p));
    }

    @Transactional
    @Commit
    @Test
    void testDeleteBy() {
        productRepository.deleteByPriceLessThan(2000L);
    }

    @Test
    void testJpql() {
        List<Product> products = productRepository.getList();
        products.forEach(p-> System.out.println(p));
    }

    @Transactional
    @Commit // 실제DB 적용
    @Test
    void testJpqlUpdate() {
        productRepository.updateProduct("수정된 상품2", 88888, 10L);
    }

    @Transactional
    @Commit
    @Test
    void testJpqlUpdate2() {
        productRepository.updateProduct2(11L,77777, "수정된 상품3");
    }

    @Transactional
    @Commit
    @Test
    void testJpqlUpdate3() {
        Product p = new Product();
        p.setName("수정된 상품4");
        p.setPrice(66666);
        p.setIdx(12L);
        productRepository.updateProduct3(p);
    }

    @Test
    void testObject() {
        Pageable pageable = PageRequest.of(0, 10,
                Sort.Direction.DESC, "crdt");
        Page<Object[]> products = productRepository.getAvgPrice(pageable);
        products.forEach(p-> System.out.println(Arrays.toString(p)));
    }

    @Test
    void testNativeSql() {
        Pageable pageable = PageRequest.of(0, 10,
                Sort.Direction.DESC, "crdt");
        Page<Object[]> products = productRepository.getNativeSqlResult(pageable);
        products.forEach(p-> System.out.println(Arrays.toString(p)));
    }

    @Test
    void testQuerydsl() {
        Pageable pageable = PageRequest.of(0, 10,
                Sort.Direction.DESC, "crdt");
        // 1. BooleanBuilder 객체 생성
        QProduct qProduct = QProduct.product;
        BooleanBuilder builder = new BooleanBuilder();

        // 2. 조건 표현식 설정
        String searchWord = "1"; // 검색어
        BooleanExpression expression = qProduct.name.contains(searchWord);

        // 3. BooleanBuidler에 조건설정 추가
        builder.and(expression);

        // 4. 실행
        Page<Product> products = productRepository.findAll(builder, pageable);
        products.forEach(p-> System.out.println(p));
    }

    @Test
    void testQuerydsl2() {
        Pageable pageable = PageRequest.of(0, 10,
                Sort.Direction.DESC, "crdt");
        // 1. BooleanBuilder 객체 생성
        QProduct qProduct = QProduct.product;
        BooleanBuilder builder = new BooleanBuilder();

        // 2. 조건 표현식 설정
        String searchWord = "1"; // 검색어
        String price = "3000"; // 가격조건 (3000원 이하)
        BooleanExpression expression1 = qProduct.name.contains(searchWord);
        BooleanExpression expression2 = qProduct.price.loe(Integer.parseInt(price));
        // 두 조건 결합
        BooleanExpression all = expression1.and(expression2);

        // 3. BooleanBuidler에 조건설정 추가
        builder.and(all);

        // 4. 실행
        Page<Product> products = productRepository.findAll(builder, pageable);
        products.forEach(p-> System.out.println(p));
    }

    @Transactional
    @Test
    void getList() {
        Pageable pageable = PageRequest.of(0, 10,
                Sort.Direction.DESC, "crdt");
        Page<Product> products = productRepository.findAll(pageable);
        products.forEach(p-> System.out.println(p.getName()+" "+p.getReviews().size()));
    }

    @Test
    void testSearch() {
        productRepository.search();
    }

    @Test
    void testSearch2() {
        productRepository.search2();
    }

    @Test
    void testSearch3() {
        Pageable pageable = PageRequest.of(0, 10,
                Sort.Direction.DESC, "crdt");
        Page<Object[]> result = productRepository.search3("all", "1", pageable);

        result.get().forEach(obj -> System.out.println(Arrays.toString(obj)));
    }
}

