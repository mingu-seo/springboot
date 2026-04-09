package com.example.chapter14.config;

import com.example.chapter14.security.handler.MemberLoginFailureHandler;
import com.example.chapter14.security.handler.MemberLoginSuccessHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Log4j2
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(auth->{
//            auth
//                    .requestMatchers("/test/all").permitAll() // 모두 허용 (인증없이 가능)
//                    .requestMatchers("/test/member").authenticated() // 로그인후 가능(인증 필요)
//                    .anyRequest().permitAll(); // 나머지 모든 URL 인증없이 허용 (매번 URL을 등록할 필요없이 한번에 처리)
//        });

        // 로그인 설정
        http.formLogin(login->{
            login.successHandler(memberLoginSuccessHandler());
            login.failureHandler(memberLoginFailuerHandler());
        });

        // 소셜로그인 설정
        http.oauth2Login(oauth2->{
            oauth2.successHandler(memberLoginSuccessHandler());
            oauth2.failureHandler(memberLoginFailuerHandler());
        });

        http.rememberMe(rm->rm.tokenValiditySeconds(60*60*24*7)); // 7일

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MemberLoginSuccessHandler memberLoginSuccessHandler() {
        return new MemberLoginSuccessHandler();
    }
    @Bean
    public MemberLoginFailureHandler memberLoginFailuerHandler() {
        return new MemberLoginFailureHandler();
    }
}
