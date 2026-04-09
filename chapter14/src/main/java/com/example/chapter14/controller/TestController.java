package com.example.chapter14.controller;

import com.example.chapter14.security.dto.AuthMemberDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {
    // 모든 사용자 접속 가능
    @GetMapping("/all")
    public void all() {
    }
    // 로그인 후 사용자권한이 있는 사용자만 접속 가능
    @GetMapping("/member")
    public void member(
            Authentication authentication, Model model,
            @AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        System.out.println(authentication);
        System.out.println(authMemberDTO);
        // authentication은 Spring이 자동 주입
        model.addAttribute("user", authentication);
    }

    // 로그인 후 관리자권한이 있는 사용자만 접속 가능
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public void admin() {
    }
}
