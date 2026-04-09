package com.example.chapter11.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "공통 응답 형식")
public class ApiResponse<T> {

    @Schema(description = "요청 성공 여부", example = "true")
    private boolean success;

    @Schema(description = "응답 데이터")
    private T data;

    @Schema(description = "응답 메시지", example = "요청이 성공했습니다.")
    private String message;
}