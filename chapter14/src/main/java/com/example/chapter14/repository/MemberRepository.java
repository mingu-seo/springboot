package com.example.chapter14.repository;

import com.example.chapter14.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmailAndIsSocial(String email, boolean isSocial);
}
