package com.example.chapter12.repository;

import com.example.chapter12.entity.Product;
import com.example.chapter12.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@SpringBootTest
public class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void insertDummyData() {
        Random random = new Random();
        for (int i = 1; i <= 100; i++) {
            long productIdx = 90 + random.nextInt(11); // 90~100
            Product product = productRepository.findById(productIdx)
                    .orElseThrow();
            Review review = Review.builder()
                    .product(product)
                    .content("리뷰 내용" + i)
                    .rating(random.nextInt(5) + 1) // 1~5
                    .build();
            reviewRepository.save(review);
        }
    }

    // 최신 리뷰 조회
    @Transactional
    @Test
    void getList() {
        Pageable pageable = PageRequest.of(0, 10,
                Sort.Direction.DESC, "crdt");
        // 리뷰 전체 조회 후 상품명 출력
        Page<Review> reviews = reviewRepository.findAll(pageable);
        // → SELECT * FROM review   (1번)

        for (Review review : reviews) {
             System.out.println(review.getProduct().getName());
            // → SELECT * FROM product WHERE idx = ?  (리뷰 건수만큼 반복!)
        }
    }
}
