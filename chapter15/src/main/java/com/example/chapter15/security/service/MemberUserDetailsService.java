package com.example.chapter15.security.service;

import com.example.chapter15.entity.Member;
import com.example.chapter15.repository.MemberRepository;
import com.example.chapter15.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("사용자가 입력한 이메일:"+username);
        Member member = memberRepository.findByEmailAndIsSocial(username, false);
        if (member == null) {
            throw new UsernameNotFoundException("이메일이 존재하지 않습니다.");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+member.getRole()));
        AuthMemberDTO authMember = new AuthMemberDTO(
                member.getEmail(),
                member.getPwd(),
                member.isSocial(),
                authorities
        );
        return authMember;
    }
}
