package com.example.chapter15.service;

import com.example.chapter15.dto.ReviewDTO;
import com.example.chapter15.entity.Product;
import com.example.chapter15.entity.Review;

import java.util.List;

public interface ReviewService {
    Long register(ReviewDTO dto); // 등록
    ReviewDTO detail(Long idx); // 상세
    List<ReviewDTO> getListWithProduct(Long product_idx); // 해당상품 리뷰리스트

    // entity -> dto
    default ReviewDTO entityToDto(Review review) {
        ReviewDTO dto = ReviewDTO.builder()
                .idx(review.getIdx())
                .content(review.getContent())
                .rating(review.getRating())
                .crdt(review.getCrdt())
                .product_idx(review.getProduct().getIdx())
                .product_name(review.getProduct().getName())
                .product_price(review.getProduct().getPrice())
                .build();
        return dto;
    }
    // dto -> entity
    default Review dtoToEtntity(ReviewDTO dto) {
        Review review = Review.builder()
                .content(dto.getContent())
                .rating(dto.getRating())
                .product(Product.builder().idx(dto.getProduct_idx()).build())
                .build();
        return review;
    }
}
