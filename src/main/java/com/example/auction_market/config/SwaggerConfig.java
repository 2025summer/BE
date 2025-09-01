package com.example.auction_market.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        // SecurityScheme 이름 (나중에 SecurityRequirement에서 참조)
        String securitySchemeName = "JWT Token";

        return new OpenAPI()
                .info(new Info()
                        .title("Used Market API")
                        .description("중고 경매 플랫폼의 API 문서입니다.")
                        .version("v1.0.0"))
                // Security 스키마 등록
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(Type.HTTP) // HTTP 인증 방식
                                        .scheme("bearer") // Bearer Token 방식
                                        .bearerFormat("JWT") // JWT 포맷
                                        .in(In.HEADER) // Authorization 헤더에 넣음
                        )
                );
    }

}
