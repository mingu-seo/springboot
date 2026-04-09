package com.example.chapter15.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
public class AuthMemberDTO extends User  {
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

}
