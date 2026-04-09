package com.example.chapter08.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        long start = System.currentTimeMillis();

        String method = request.getMethod();
        String uri = request.getRequestURI();

        try {
            System.out.println("앞에서 실행");
            filterChain.doFilter(request, response);
            System.out.println("뒤에서 실행");
        } finally {
            long elapsed = System.currentTimeMillis() - start;
            int status = response.getStatus();

            System.out.printf("[FILTER] %s %s -> %d (%dms)%n", method, uri, status, elapsed);
        }
    }
}
