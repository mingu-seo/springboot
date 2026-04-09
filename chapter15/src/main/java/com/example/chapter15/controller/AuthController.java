package com.example.chapter15.controller;

import com.example.chapter15.dto.MemberDTO;
import com.example.chapter15.entity.Client;
import com.example.chapter15.entity.Member;
import com.example.chapter15.repository.ClientRepository;
import com.example.chapter15.repository.MemberRepository;
import com.example.chapter15.security.dto.AuthMemberDTO;
import com.example.chapter15.security.dto.ClientAuthRequest;
import com.example.chapter15.security.jwt.ClientJwtProvider;
import com.example.chapter15.security.jwt.MemberJwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final MemberJwtProvider memberJwtProvider;
    private final MemberRepository memberRepository;
    private final ClientJwtProvider clientJwtProvider;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody MemberDTO request) {
        log.info("로그인 시도: {}", request.getEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPwd())
        );

        AuthMemberDTO principal = (AuthMemberDTO) authentication.getPrincipal();
        List<String> roles = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String primaryRole = roles.isEmpty() ? "ROLE_USER" : roles.get(0);
        String accessToken = memberJwtProvider.createAccessToken(principal.getEmail(), primaryRole);
        String refreshToken = memberJwtProvider.createRefreshToken(principal.getEmail());

        return Map.of(
                "email", principal.getEmail(),
                "roles", roles,
                "tokenType", "Bearer",
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }

    @PostMapping("/refresh")
    public Map<String, Object> refresh(@RequestBody Map<String, String> body) {
        // 바디에서 refreshToken 추출
        String refreshToken = body.get("refreshToken");

        // memberJwtProvider.validateToken() 검증
        if (refreshToken == null || !memberJwtProvider.validateToken(refreshToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 refresh token");
        }

        // 토큰에서 email 꺼내 Member 조회
        String email = memberJwtProvider.getEmail(refreshToken);
        Member member = memberRepository.findByEmailAndIsSocial(email, false);
        if (member == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "존재하지 않는 사용자");
        }

        String role = "ROLE_" + member.getRole().name();
        // 현재 role과 이메일로 새 access token 발급
        String newAccessToken = memberJwtProvider.createAccessToken(email, role);

        log.info("access token 재발급: {}", email);

        return Map.of(
                "tokenType", "Bearer",
                "accessToken", newAccessToken
        );
    }

    @PostMapping("/client-token")
    public Map<String, Object> issueClientToken(@RequestBody ClientAuthRequest request) {
        log.info("클라이언트 토큰 발급 요청: {}", request.getClientId());

        Client client = clientRepository.findByClientId(request.getClientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "클라이언트 인증 실패"));

        if (!client.isActive()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비활성화된 클라이언트");
        }
        if (!passwordEncoder.matches(request.getClientSecret(), client.getClientSecret())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "클라이언트 인증 실패");
        }

        String clientToken = clientJwtProvider.createToken(client.getClientId(), client.getName());

        return Map.of(
                "tokenType", "Bearer",
                "clientToken", clientToken,
                "expiresInMs", clientJwtProvider.getExpMillis()
        );
    }
}
