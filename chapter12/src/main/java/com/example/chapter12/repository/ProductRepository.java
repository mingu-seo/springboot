package com.example.chapter12.repository;

import com.example.chapter12.entity.Product;
import com.example.chapter12.entity.Review;
import com.example.chapter12.repository.search.SearchProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>,
        QuerydslPredicateExecutor<Product>, SearchProductRepository {

    List<Product> findByPriceBetweenOrderByCrdtDesc(Long from, Long to);
    Page<Product> findByPriceBetween(Long from, Long to, Pageable pageable);

    void deleteByPriceLessThan(Long price);

    // JPQL
    @Query("select p from Product p order by p.idx desc")
    List<Product> getList();

    @Modifying
    @Query("update Product p set p.name=?1, p.price=?2 where p.idx=?3 ")
    void updateProduct(String name, Integer price, Long idx);

    @Modifying
    @Query("update Product p set p.name=:name, p.price=:price where p.idx=:idx ")
    void updateProduct2(@Param("idx")Long i, @Param("price") Integer p, @Param("name")String n);

    @Modifying
    @Query("update Product p set p.name=:#{#param.name}, p.price=:#{#param.price} where p.idx=:#{#param.idx} ")
    void updateProduct3(@Param("param") Product p);

    @Query("select avg(p.price), now() from Product p")
    Page<Object[]>  getAvgPrice(Pageable pageable);

    @Query(value="select name, price, price*0.1 from product where price < 3000", nativeQuery = true)
    Page<Object[]>  getNativeSqlResult(Pageable pageable);

    @EntityGraph(attributePaths = {"reviews"})
    public Page<Product> findAll(Pageable pageable);
}
