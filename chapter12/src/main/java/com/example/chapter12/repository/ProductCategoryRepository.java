package com.example.chapter12.repository;

import com.example.chapter12.entity.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    // 특정 상품의 카테고리 목록 (Fetch Join으로 N+1 해결)
    @Query("SELECT pc FROM ProductCategory pc " +
            "JOIN FETCH pc.category " +
            "WHERE pc.product.idx = :productIdx")
    List<ProductCategory> findByProductIdx(@Param("productIdx") Long productIdx);

    // 특정 카테고리의 상품 목록 (Fetch Join + 페이징)
    @Query(
            value = "SELECT pc FROM ProductCategory pc " +
                    "JOIN FETCH pc.product " +
                    "WHERE pc.category.idx = :categoryIdx",
            countQuery = "SELECT COUNT(pc) FROM ProductCategory pc " +
                    "WHERE pc.category.idx = :categoryIdx"
    )
    Page<ProductCategory> findByCategoryIdx(@Param("categoryIdx") Long categoryIdx,
                                            Pageable pageable);
}