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
public class ClientJwtProvider {

    private final JwtProperties jwtProperties;

    private SecretKey key;
    private long expMillis;

    @PostConstruct
    void init() {
        JwtProperties.Client props = jwtProperties.getClient();
        this.key = Keys.hmacShaKeyFor(props.getSecret().getBytes(StandardCharsets.UTF_8));
        this.expMillis = props.getExpMin() * 60 * 1000;
    }

    public String createToken(String clientId, String clientName) {
        Date now = new Date();
        return Jwts.builder()
                .subject(clientId)
                .claim("clientName", clientName)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expMillis))
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            log.warn("Client JWT 검증 실패: {}", e.getMessage());
            return false;
        }
    }

    public String getClientId(String token) {
        return parseClaims(token).getSubject();
    }

    public long getExpMillis() {
        return expMillis;
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
