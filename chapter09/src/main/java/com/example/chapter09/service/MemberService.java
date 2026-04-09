package com.example.chapter09.service;

import com.example.chapter09.domain.Member;
import com.example.chapter09.mappers.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    private final MemberMapper memberMapper;

    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public void save(Member member) {
        memberMapper.insert(member);
    }

    public List<Member> findAll() {
        return memberMapper.findAll();
    }

}