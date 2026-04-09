package com.example.chapter12.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "category")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@ToString
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String name;            // 카테고리명 (전자제품, 컴퓨터 등)
    private String description;     // 카테고리 설명

    @CreatedDate
    private LocalDateTime crdt;

    @Builder
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}