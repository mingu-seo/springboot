package com.example.chapter08.controller;


import com.example.chapter08.dto.LoginUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/my")
public class MyPageController {

    @GetMapping("/mypage")
    public String mypage(HttpSession session,
                         Model model,
                         RedirectAttributes ra
    ) {

        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
//        if (loginUser == null) {
//            ra.addFlashAttribute("error", "로그인 후 접속 가능합니다.");
//            return "redirect:/auth/login";
//        }

        model.addAttribute("loginUser", loginUser);
        return "my/mypage";
    }
}