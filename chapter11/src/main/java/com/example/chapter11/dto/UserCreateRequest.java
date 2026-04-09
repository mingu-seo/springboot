package com.example.chapter11.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "회원 등록 요청")
public class UserCreateRequest {

    @Schema(description = "회원 이름", example = "이순신")
    private String name;

    @Schema(description = "회원 이메일", example = "lee@test.com")
    private String email;
}