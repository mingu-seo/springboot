package com.example.chapter15.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="product")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString(exclude = "reviews")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String name;
    private Integer price;
    @CreatedDate
    private LocalDateTime crdt;

    // 1:N
    // mappedBy = Review엔티티의 필드명
    @Builder.Default
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    // ProductCategory와의 관계
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductCategory> productCategories = new ArrayList<>();
}
