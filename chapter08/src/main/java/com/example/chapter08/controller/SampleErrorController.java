package com.example.chapter08.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sample")
public class SampleErrorController {

    @GetMapping("/bad")
    public String bad() {
        throw new IllegalArgumentException("잘못된 요청 파라미터입니다.");
    }

    @GetMapping("/error")
    public String error() {
        throw new RuntimeException("예상하지 못한 오류");
    }
}