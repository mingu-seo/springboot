package com.example.chapter15.repository;

import com.example.chapter15.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmailAndIsSocial(String email, boolean isSocial);
}
