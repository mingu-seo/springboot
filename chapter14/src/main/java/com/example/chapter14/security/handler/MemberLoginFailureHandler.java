package com.example.chapter14.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
public class MemberLoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("로그인 실패시 처리");
        // 로그인 실패 → /login?error로 리다이렉트 (에러 표시)
        // FailureHandler 등록시 필터가 통과되서 DispatchServlet으로 도달하게 되어 에러 발생
        response.sendRedirect("/login?error");
    }
}
