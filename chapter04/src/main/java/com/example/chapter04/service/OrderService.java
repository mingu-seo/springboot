package com.example.chapter04.service;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public void order() {
        System.out.println("주문 처리 로직 실행");
    }
}
