package com.example.chapter15.repository.search;

import com.example.chapter15.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchProductRepository {
    void search();
    void search2();
    Page<Object[]> search3(String type, String word, Pageable pageable);
}
