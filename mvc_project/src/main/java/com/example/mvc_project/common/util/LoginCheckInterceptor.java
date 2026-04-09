package com.example.mvc_project.common.util;

import com.example.mvc_project.member.dto.MemberDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        HttpSession session = request.getSession();
        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");

        if (loginMember == null) {
            response.setContentType("text/html; charset=utf-8");
            var out = response.getWriter();
            out.print("<script>");
            out.print("alert('로그인 후 사용가능합니다.');");
            out.print("location.href='/member/login';");
            out.print("</script>");
            out.close();
            return false;
        }
        return true;
    }
}