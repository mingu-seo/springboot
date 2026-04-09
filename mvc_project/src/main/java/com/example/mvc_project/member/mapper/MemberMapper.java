package com.example.mvc_project.member.mapper;

import com.example.mvc_project.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    int join(MemberDTO dto);

    int emailCheck(String email);
    MemberDTO login(MemberDTO dto);
}