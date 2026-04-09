package com.example.chapter12.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_category")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@ToString(exclude = {"product", "category"})
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_idx")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_idx")
    private Category category;

    @CreatedDate
    private LocalDateTime crdt;     // 카테고리 등록일

    @Builder
    public ProductCategory(Product product, Category category) {
        this.product = product;
        this.category = category;
    }
}