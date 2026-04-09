package com.example.chapter08.config;

import com.example.chapter08.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/my/**")
                .excludePathPatterns(
                        "/auth/**",
                        "/css/**", "/js/**", "/images/**",
                        "/error"
                );
    }
}
