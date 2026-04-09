package com.example.chapter15.service;

import com.example.chapter15.dto.ReviewDTO;
import com.example.chapter15.entity.Review;
import com.example.chapter15.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public Long register(ReviewDTO dto) {
        Review review = dtoToEtntity(dto);
        reviewRepository.save(review);
        return review.getIdx();
    }

    @Override
    public ReviewDTO detail(Long idx) {
        Optional<Review> review = reviewRepository.findById(idx);
        if (review.isPresent()) {
            return entityToDto(review.get());
        }
        return null;
    }

    @Override
    public List<ReviewDTO> getListWithProduct(Long product_idx) {
        Pageable pageable = PageRequest.of(0, 10,
                Sort.Direction.DESC, "crdt");
        Page<Review> reviews = reviewRepository.findAll(pageable);
        List<ReviewDTO> reviewList = reviews.get().map(r->entityToDto(r)).collect(Collectors.toList());
        return reviewList;
    }
}
