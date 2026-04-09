package com.example.chapter08.interceptor;

import com.example.chapter08.dto.LoginUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        HttpSession session = request.getSession(false);
        LoginUser loginUser = (session == null) ? null : (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            // 로그인 페이지로 리다이렉트
            response.sendRedirect("/auth/login");
            return false;
        }

        return true;
    }
}
