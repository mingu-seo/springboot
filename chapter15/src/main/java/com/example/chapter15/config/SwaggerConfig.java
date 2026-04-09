package com.example.chapter15.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Security Scheme 정의
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
        // 클라이언트 인증
        SecurityScheme clientScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("X-Client-Token");

        SecurityRequirement requirement = new SecurityRequirement()
                .addList("BearerAuth")
                .addList("ClientAuth");

        return new OpenAPI()
                .info(new Info().title("Chapter15 API")
                        .description("Review/JWT 실습용 API 문서")
                        .version("v1.0"))
                .addSecurityItem(requirement)  // Security Requirement 추가
                .schemaRequirement("BearerAuth", securityScheme)  // Security Scheme 추가
                .schemaRequirement("ClientAuth", clientScheme);
    }
}