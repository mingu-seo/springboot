package com.example.mvc_project.member.service;

import com.example.mvc_project.member.dto.MemberDTO;

public interface MemberService {

    boolean join(MemberDTO dto);

    int emailCheck(String email);

    MemberDTO login(MemberDTO dto);
}

