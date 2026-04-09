package com.example.chapter15.config;

import com.example.chapter15.security.jwt.ClientAuthenticationFilter;
import com.example.chapter15.security.jwt.ClientJwtProvider;
import com.example.chapter15.security.jwt.JwtAuthenticationFilter;
import com.example.chapter15.security.jwt.MemberJwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Log4j2
public class SecurityConfig {

    private final MemberJwtProvider memberJwtProvider;
    private final ClientJwtProvider clientJwtProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/review/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/review/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/review/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/review/**").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .addFilterBefore( // 인증필터앞에 JWT필터 삽입
                        new JwtAuthenticationFilter(memberJwtProvider),
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterBefore( // JWT 회원 필터 앞에 클라이언트 인증 필터
                        new ClientAuthenticationFilter(clientJwtProvider),
                        JwtAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
