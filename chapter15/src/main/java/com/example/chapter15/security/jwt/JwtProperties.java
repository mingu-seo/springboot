package com.example.chapter15.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private Member member = new Member();
    private Client client = new Client();

    @Getter
    @Setter
    public static class Member {
        private String secret;
        private long accessExpMin;
        private long refreshExpMin;
    }

    @Getter
    @Setter
    public static class Client {
        private String secret;
        private long expMin;
    }
}
