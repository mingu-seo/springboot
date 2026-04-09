package com.example.chapter07;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MemberController {

    @GetMapping("/member/index.do")
    public String index(Model model) {
        model.addAttribute("name", "홍길동");

        MemberDTO member = new MemberDTO();
        member.setId("lee");
        member.setName("이순신");
        member.setAge(30);
        model.addAttribute("member", member);

        // 리스트
        List<MemberDTO> memberList = new ArrayList<>();
        memberList.add(member);
        member = new MemberDTO();
        member.setId("lee");
        member.setName("이순신");
        member.setAge(30);
        memberList.add(member);
        member = new MemberDTO();
        member.setId("kim");
        member.setName("김유신");
        member.setAge(40);
        memberList.add(member);

        model.addAttribute("list", memberList);


        return "member/index"; // resources/templatges/member/index.html
    }

    @GetMapping("/member/form.do")
    public String form(Model model) {
        MemberDTO dto = new MemberDTO();
        dto.setId("hong");
        dto.setName("홍길동");
        model.addAttribute("member", dto);
        model.addAttribute("agree", "checked");
        model.addAttribute("isActive", true);
        model.addAttribute("regdate", LocalDateTime.now());
        model.addAttribute("price", 100000);
        return "member/form";
    }

}
