package com.example.chapter08.config;

import com.example.chapter08.filter.RequestLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RequestLoggingFilter> requestLoggingFilter() {
        FilterRegistrationBean<RequestLoggingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new RequestLoggingFilter());
        bean.addUrlPatterns("/*");      // 전체 요청
        bean.setOrder(1);               // 필터 순서(낮을수록 먼저)
        return bean;
    }
}
