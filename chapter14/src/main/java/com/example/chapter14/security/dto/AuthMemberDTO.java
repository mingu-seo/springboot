package com.example.chapter14.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
public class AuthMemberDTO extends User implements OAuth2User {
    // 추가 필드
    private String email;
    private String name;
    private boolean isSocial;
    private Map<String, Object> attr;

    public AuthMemberDTO(String username, @Nullable String password,
                         boolean isSocial,
                         Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        // 추가 필드 세팅
        this.email = username;
        this.isSocial = isSocial;
    }

    // 소셜로그인용 생성자
    public AuthMemberDTO(String username, @Nullable String password,
                         boolean isSocial,
                         Collection<? extends GrantedAuthority> authorities,
                         Map<String, Object> attr) {
        super(username, password, authorities);
        // 추가 필드 세팅
        this.email = username;
        this.isSocial = isSocial;
        this.attr = attr;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }
}
