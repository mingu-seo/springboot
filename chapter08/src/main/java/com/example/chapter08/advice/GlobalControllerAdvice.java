package com.example.chapter08.advice;

import com.example.chapter08.dto.LoginUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    // 모든 컨트롤러의 Model에 공통 데이터 주입
    @ModelAttribute("currentUser")
    public LoginUser currentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;
        return (LoginUser) session.getAttribute("loginUser");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleBadRequest(IllegalArgumentException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error/bad-request";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("message", "요청 처리 중 문제가 발생했습니다.");
        model.addAttribute("detail", e.getClass().getSimpleName());
        return "error/error";
    }
}
