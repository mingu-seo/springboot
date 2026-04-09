package com.example.chapter11.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "회원 수정 요청")
public class UserUpdateRequest {

    @Schema(description = "회원 이름", example = "강감찬")
    private String name;

    @Schema(description = "회원 이메일", example = "kang@test.com")
    private String email;
}