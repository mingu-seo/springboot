package com.example.chapter14.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
public class MemberLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
       log.info("로그인 성공시 처리");
        String username = authentication.getName(); // 로그인한 사용자 ID
        String ipAddress = request.getRemoteAddr(); // 클라이언트 IP 주소

        log.info("사용자 로그인 성공: 사용자={}, IP={}", username, ipAddress);

        // 이전 요청 URL 가져오기
        SavedRequest savedRequest = (SavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        String redirectUrl = "";
        if (savedRequest != null) {
            redirectUrl = savedRequest.getRedirectUrl();
        } else {
            redirectUrl = "/";
        }
        response.sendRedirect(redirectUrl);
    }
}
