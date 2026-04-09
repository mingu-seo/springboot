package com.example.chapter15.controller;

import com.example.chapter15.dto.ReviewDTO;
import com.example.chapter15.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/list")
    public ResponseEntity<List<ReviewDTO>> list() {
        List<ReviewDTO> list = reviewService.getListWithProduct(1L);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody ReviewDTO dto) {
        Long idx = reviewService.register(dto);
        return new ResponseEntity<>(idx, HttpStatus.OK);
    }

    @GetMapping("/detail/{idx}")
    public ResponseEntity<ReviewDTO> detail(@PathVariable Long idx) {
        ReviewDTO dto = reviewService.detail(idx);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
