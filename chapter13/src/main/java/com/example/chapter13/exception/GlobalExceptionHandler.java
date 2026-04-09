package com.example.chapter13.exception;

import com.example.chapter13.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Validation 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponse response = ErrorResponse.builder()
                .code("VALIDATION_ERROR")
                .message("입력값이 올바르지 않습니다.")
                .timestamp(LocalDateTime.now())
                .validationErrors(errors)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    // 일반 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {

        ErrorResponse response = ErrorResponse.builder()
                .code("INTERNAL_SERVER_ERROR")
                .message("서버 내부 오류")
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .internalServerError()
                .body(response);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFound(ProductNotFoundException e) {

        ErrorResponse response = ErrorResponse.builder()
                .code("PRODUCT_NOT_FOUND")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(404)
                .body(response);
    }
}