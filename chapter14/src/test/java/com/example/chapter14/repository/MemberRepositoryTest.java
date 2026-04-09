package com.example.chapter14.repository;

import com.example.chapter14.entity.Member;
import com.example.chapter14.entity.MemberRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsertMemberPerRole() {
        // USER 등록
        Member user = new Member();
        user.setName("홍길동");
        user.setEmail("user@example.com");
        user.setPwd(passwordEncoder.encode("1234"));
        user.setSocial(false);
        user.setRole(MemberRole.USER);
        memberRepository.save(user);

        // MANAGER 등록
        Member manager = new Member();
        manager.setName("김매니저");
        manager.setEmail("manager@example.com");
        manager.setPwd(passwordEncoder.encode("1234"));
        manager.setSocial(false);
        manager.setRole(MemberRole.MANAGER);
        memberRepository.save(manager);

        // ADMIN 등록
        Member admin = new Member();
        admin.setName("박관리자");
        admin.setEmail("admin@example.com");
        admin.setPwd(passwordEncoder.encode("1234"));
        admin.setSocial(false);
        admin.setRole(MemberRole.ADMIN);
        memberRepository.save(admin);
    }

    @Test
    void getMember() {
        Member member = memberRepository.findByEmailAndIsSocial("user@example.com", false);
        System.out.println(member);
    }
}
