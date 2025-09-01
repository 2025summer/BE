package com.example.auction_market.config;

import com.example.auction_market.security.MultipartJackson2HttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final MultipartJackson2HttpMessageConverter multipartJackson2HttpMessageConverter;

    public WebConfig(MultipartJackson2HttpMessageConverter converter) {
        this.multipartJackson2HttpMessageConverter = converter;
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 최우선으로 시도되도록 0번에 추가
        converters.add(0, multipartJackson2HttpMessageConverter);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 모든 경로 허용
                        .allowedOrigins(
                                "http://localhost:8080",   // 로컬 Swagger
                                "http://localhost:3000",   // 로컬 프론트
                                "https://fe-mauve-one.vercel.app/", // 배포된 프론트
                                "https://port-0-auction-market-be-me1e307hfc5bab5c.sel5.cloudtype.app"  // Swagger UI 배포 주소
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true); // 인증정보 포함 허용 (JWT 등)
            }
        };
    }
}
