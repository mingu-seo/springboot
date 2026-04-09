package com.example.chapter09.mapper;

import com.example.chapter09.domain.Member;
import com.example.chapter09.mappers.MemberMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MemberMapperTest {
    @Autowired
    private MemberMapper memberMapper;

    @Test
    public void selectTest() {
        // 주입받은 객체명 출력
        System.out.println(memberMapper.getClass().getName());
    }

    @Test
    public void findAllTest() {
        System.out.println(memberMapper.findAll());
    }

    @Test
    public void insertWithKeyTest() {
        Member member = new Member();
        member.setMName("김길동");
        member.setMEmail("kim@gmail.com");
        member.setMAge(30);
        System.out.println("insert 전:"+member.getMId());
        memberMapper.insertWithKey(member);
        System.out.println("insert 후:"+member.getMId());
    }

    @Test
    @Transactional
    @Commit
    public void rollbackTest() {
        Member member1 = new Member();
        member1.setMName("최길동");
        member1.setMEmail("choi@gmail.com");
        member1.setMAge(30);

        Member member2 = new Member();
        member2.setMName("최길동");
        member2.setMEmail("choi@gmail.com");
        member2.setMAge(30);
        memberMapper.insert(member1);
//        memberMapper.insert(member2); // UNIQUE 오류
    }
}
