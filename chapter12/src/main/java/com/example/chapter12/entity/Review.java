package com.example.chapter12.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@ToString(exclude = "product")  // ← 무한참조 방지
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_idx")   // ← FK 컬럼명 지정
    private Product product;

    private String content;             // 리뷰 내용

    private Integer rating;             // 별점 (1~5)

    @CreatedDate
    private LocalDateTime crdt;

    @Builder
    public Review(Product product, String content, Integer rating) {
        this.product = product;
        this.content = content;
        this.rating = rating;
    }

    // 리뷰 수정 메서드
    public void update(String content, Integer rating) {
        this.content = content;
        this.rating = rating;
    }
}