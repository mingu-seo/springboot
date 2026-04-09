package com.example.chapter03;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public Person person2() {
        Person p = new Person();
        p.setName("홍길동");
        return p;
    }

    @Bean
    public MemberDAO memberDAO() {
        return new MemberDAO();
    }

    @Bean
    public MemberService memberService() {
        MemberServiceImpl m = new MemberServiceImpl();
        m.setDao(memberDAO()); // 빈 주입
        return m;
    }

}
