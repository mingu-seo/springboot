package com.example.chapter13.controller;

import com.example.chapter13.dto.ProductCreateRequest;
import com.example.chapter13.exception.ProductNotFoundException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/products")
public class ProductController {

    @PostMapping
    public String createProduct(@Valid @RequestBody ProductCreateRequest request) {
        return "상품 등록 성공";
    }

    @GetMapping("/{id}")
    public String getProduct(@PathVariable Long id) {
        throw new ProductNotFoundException("상품을 찾을 수 없습니다. ID: " + id);
    }
}