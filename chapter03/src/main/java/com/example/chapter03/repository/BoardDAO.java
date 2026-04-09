package com.example.chapter03.repository;

import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO {

    public void save() {
        System.out.println("게시글 저장");
    }
}