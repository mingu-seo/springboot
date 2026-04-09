package com.example.chapter15.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long idx;
    private String content;
    private Integer rating;
    private LocalDateTime crdt;
    private Long product_idx;
    private String product_name;
    private Integer product_price;
}
