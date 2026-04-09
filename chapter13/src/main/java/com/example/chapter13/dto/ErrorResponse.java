package com.example.chapter13.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
public class ErrorResponse {

    private String code;        // 에러 코드
    private String message;     // 에러 메시지
    private LocalDateTime timestamp;
    private Map<String, String> validationErrors;
}