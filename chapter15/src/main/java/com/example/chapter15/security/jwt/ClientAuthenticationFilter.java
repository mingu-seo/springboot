package com.example.chapter15.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class ClientAuthenticationFilter extends OncePerRequestFilter {

    private static final String CLIENT_HEADER = "X-Client-Token";

    private static final List<String> EXCLUDED_PATHS = List.of(
            "/api/auth/client-token",
            "/api/auth/login",
            "/api/auth/refresh",
            "/swagger-ui",
            "/v3/api-docs"
    );

    private final ClientJwtProvider clientJwtProvider;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return EXCLUDED_PATHS.stream().anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader(CLIENT_HEADER);

        if (!StringUtils.hasText(token) || !clientJwtProvider.validateToken(token)) {
            log.warn("클라이언트 인증 실패: {}", request.getRequestURI());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"status\":401,\"message\":\"클라이언트 인증 실패\"}");
            return;
        }

        log.debug("클라이언트 인증 성공: {}", clientJwtProvider.getClientId(token));
        filterChain.doFilter(request, response);
    }
}
