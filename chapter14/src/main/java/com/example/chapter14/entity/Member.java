package com.example.chapter14.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "s_member")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String name;
    private String email;
    private String pwd;
    private boolean isSocial;

    @CreatedDate // main 메서드에 @EnableJpaAuditing 추가
    private LocalDateTime crdt;

    private MemberRole role;

}
