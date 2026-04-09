package com.example.chapter09.mappers;

import com.example.chapter09.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {

    int insert(Member member);

    int update(Member member);

    int deleteById(@Param("mId") Long mId);

    Optional<Member> findById(@Param("mId") Long mId);

    List<Member> findAll();

    int insertWithKey(Member member);
}