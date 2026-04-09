package com.example.chapter03;

import com.example.chapter03.controller.BoardController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Chapter03Application {

    public static void main(String[] args) {

//        SpringApplication.run(Chapter03Application.class, args);

        ApplicationContext ctx =
                SpringApplication.run(Chapter03Application.class, args);
        Person p = (Person)ctx.getBean("person");
        Person p2 = (Person)ctx.getBean("person2");
        System.out.println(p == p2);
        System.out.println(p2.getName());

        MemberService service = ctx.getBean("memberService", MemberService.class);
        service.regist();

        BoardController controller = ctx.getBean(BoardController.class);
        controller.write();
    }

    @Bean // 빈 설정
    public Person person() { // 메서드명 : 빈이름
        return new Person();
    }

}
