package com.example.mvc_project.member;

import com.example.mvc_project.member.dto.MemberDTO;
import com.example.mvc_project.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /** 회원가입 폼 */
    @GetMapping("/join")
    public String joinForm() {
        return "member/join";
    }

    /** 회원가입 처리 */
    @PostMapping("/join")
    public String join(MemberDTO dto, RedirectAttributes rttr) {
        boolean result = memberService.join(dto);
        if (result) {
            rttr.addFlashAttribute("msg", "정상적으로 회원가입되었습니다.");
            return "redirect:/member/login";
        } else {
            rttr.addFlashAttribute("msg", "회원가입 오류");
            return "redirect:/member/join";
        }
    }

    /** 이메일 중복확인 (AJAX) */
    @GetMapping("/emailCheck")
    @ResponseBody
    public int emailCheck(@RequestParam String email) {
        return memberService.emailCheck(email);
    }

    /** 로그인 폼 */
    @GetMapping("/login")
    public String loginForm() {
        return "member/login";
    }

    /** 로그인 처리 */
    @PostMapping("/login")
    public String login(MemberDTO dto, HttpSession session, RedirectAttributes rttr) {
        MemberDTO loginMember = memberService.login(dto);
        if (loginMember == null) {
            rttr.addFlashAttribute("msg", "아이디 비밀번호가 올바르지 않습니다.");
            return "redirect:/member/login";
        }
        session.setAttribute("loginMember", loginMember);
        return "redirect:/";
    }

    /** 로그아웃 */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
