package com.example.mvc_project.member.service;

import com.example.mvc_project.member.dto.MemberDTO;
import com.example.mvc_project.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Override
    public boolean join(MemberDTO dto) {
        return memberMapper.join(dto) > 0;
    }

    @Override
    public int emailCheck(String email) {
        return memberMapper.emailCheck(email);
    }

    @Override
    public MemberDTO login(MemberDTO dto) {
        return memberMapper.login(dto);
    }
}