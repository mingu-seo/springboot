package com.example.chapter12.repository;

import com.example.chapter12.entity.Product;
import com.example.chapter12.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>,
        QuerydslPredicateExecutor<Review> {
//    @EntityGraph(attributePaths = {"product"})
//    public Page<Review> findAll(Pageable pageable);

    @Query("SELECT r FROM Review r JOIN FETCH r.product")
    public Page<Review> findAll(Pageable pageable);
}
