package com.example.chapter09.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Member {
    private Long mId;
    private String mName;
    private String mEmail;
    private Integer mAge;
    private LocalDateTime mCrdt;
    private LocalDateTime mUpdt;
}