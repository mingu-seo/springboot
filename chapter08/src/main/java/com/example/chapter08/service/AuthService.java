package com.example.chapter08.service;

import com.example.chapter08.dto.LoginUser;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    // 실습용: 아이디/비번 고정
    public LoginUser login(String username, String password) {
        if ("hong".equals(username) && "1234".equals(password)) {
            return new LoginUser(1L, "홍길동", "USER");
        }
        if ("admin".equals(username) && "1234".equals(password)) {
            return new LoginUser(2L, "관리자", "ADMIN");
        }
        return null;
    }
}
