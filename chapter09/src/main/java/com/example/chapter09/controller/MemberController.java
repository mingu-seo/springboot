package com.example.chapter09.controller;

import com.example.chapter09.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("members", memberService.findAll());
        return "members/list";
    }
}