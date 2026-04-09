package com.example.chapter15.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Log4j2
@Component
@RequiredArgsConstructor
public class MemberJwtProvider {

    private final JwtProperties jwtProperties;

    private SecretKey key;
    private long accessExpMillis;
    private long refreshExpMillis;

    @PostConstruct
    void init() {
        JwtProperties.Member props = jwtProperties.getMember();
        this.key = Keys.hmacShaKeyFor(props.getSecret().getBytes(StandardCharsets.UTF_8));
        this.accessExpMillis = props.getAccessExpMin() * 60 * 1000;
        this.refreshExpMillis = props.getRefreshExpMin() * 60 * 1000;
    }

    public String createAccessToken(String email, String role) {
        Date now = new Date();
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + accessExpMillis))
                .signWith(key)
                .compact();
    }

    public String createRefreshToken(String email) {
        Date now = new Date();
        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + refreshExpMillis))
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            log.warn("JWT 검증 실패: {}", e.getMessage());
            return false;
        }
    }

    public String getEmail(String token) {
        return parseClaims(token).getSubject();
    }

    public String getRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
