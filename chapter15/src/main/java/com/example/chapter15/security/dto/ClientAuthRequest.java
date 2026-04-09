package com.example.chapter15.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ClientAuthRequest {
    private String clientId;
    private String clientSecret;
}
