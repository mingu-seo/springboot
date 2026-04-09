package com.example.chapter06.controller;

import com.example.chapter06.dto.LoginUser;
import com.example.chapter06.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String loginForm(
            @CookieValue(value = "rememberId", required = false) String rememberId,
            Model model
    ) {
        model.addAttribute("rememberId", rememberId);
        return "auth/login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam(required = false) String remember,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {
        LoginUser loginUser = authService.login(username, password);
        if (loginUser == null) {
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            model.addAttribute("rememberId", username); // UX: 입력값 유지
            return "auth/login";
        }

        // 1) 세션에 로그인 정보 저장
        HttpSession session = request.getSession(); // 세션이 없으면 생성
        session.setAttribute("loginUser", loginUser);

        // 2) 아이디 기억하기(쿠키)
        if ("on".equals(remember)) {
            Cookie cookie = new Cookie("rememberId", username);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 7); // 7일
            cookie.setHttpOnly(true); // JS 접근 제한
            response.addCookie(cookie);
        } else {
            // 체크 해제 시 쿠키 삭제
            Cookie cookie = new Cookie("rememberId", "");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }

        return "redirect:/my/mypage";
    }

    // 로그아웃
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 없으면 null
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return "redirect:/auth/login";
    }
}