package com.example.chapter14.security.service;

import com.example.chapter14.entity.Member;
import com.example.chapter14.entity.MemberRole;
import com.example.chapter14.repository.MemberRepository;
import com.example.chapter14.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class OAuth2UserDetailsService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("--------------------");
        log.info("userRequest: "+userRequest);
        String clientName = userRequest.getClientRegistration().getClientName();
        log.info("clientName: "+clientName);
        log.info(userRequest.getAdditionalParameters());
        log.info("================");
        oAuth2User.getAttributes().forEach((k,v) -> {
            log.info(k+":"+v);
        });

        // 가입처리
        String email = null;
        if (clientName.equals("Google")) {
            email = oAuth2User.getAttribute("email");
        }
        log.info("email: "+email);
        Member member = saveSocialMember(email);

        // AuthMemberDTO 적용
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+member.getRole()));
        AuthMemberDTO authMemberDTO = new AuthMemberDTO(
                member.getEmail(),
                member.getPwd(),
                true,
                authorities,
                oAuth2User.getAttributes()
        );
        authMemberDTO.setName(member.getName());

        return authMemberDTO;
    }

    private Member saveSocialMember(String email) {
        Member result = memberRepository.findByEmailAndIsSocial(email, true);
        if (result != null) {
            return result;
        }
        Member member = new Member();
        member.setEmail(email);
        member.setName(email);
        member.setPwd(passwordEncoder.encode("1111"));
        member.setSocial(true);
        member.setRole(MemberRole.USER);
        memberRepository.save(member);
        return member;
    }
}
