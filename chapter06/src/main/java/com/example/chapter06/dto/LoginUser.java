package com.example.chapter06.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUser {
    private Long userId;
    private String username;
    private String role;

}