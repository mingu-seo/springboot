package com.example.chapter06;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member")
public class MemberController {
    @RequestMapping("/index.do")
    public String index() {
        return "member/index";
    }
    @RequestMapping({"/index2.do", "/member.do"})
    public String index2() {
        return "member/index2";
    }

    @GetMapping("/param1.do")
    public String param1(HttpServletRequest req) {
        String id = req.getParameter("id");
        System.out.println("id:"+id);
        return "member/index";
    }

    @GetMapping("/param2.do")
    public String param2(
            @RequestParam(name="id",
                    required=false,
                    defaultValue="nobody") String ids,
            int age) {
        System.out.println("id:"+ids);
        System.out.println("age:"+age);
        return "member/index";
    }

    @GetMapping("/param3.do")
    public String param3(MemberVO vo) {
        System.out.println(vo);
        return "member/index";
    }

    @GetMapping("/view/{id}/{name}")
    public String param4(@PathVariable String id,
                         @PathVariable String name) {
        System.out.println("id:"+id);
        System.out.println("name:"+name);
        return "member/index";
    }

}
