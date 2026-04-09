package com.example.mvc_project.member.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDTO {

    private int no;
    private String email;
    private String pwd;
    private String name;
    private int gender;
    private String birthday;
    private String hp;
    private String zipcode;
    private String addr1;
    private String addr2;
    private LocalDateTime regdate;
    private int state;
}