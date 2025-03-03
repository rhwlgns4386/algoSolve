package org.example.message_queue.config;


import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class MessageQueueWebSecurityConfig {

    @Bean
    @Order(0)
    public SecurityFilterChain messageQueueFilterChain(HttpSecurity httpSecurity) throws Exception {
        defaultHttpSecurity(httpSecurity);

        httpSecurity.securityMatcher("/api/v1/problem");
        httpSecurity.cors((httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource())));
        httpSecurity.authorizeHttpRequests(
                (auth) -> auth.anyRequest().permitAll());
        return httpSecurity.build();
    }


    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration defaultCorsConfig = new CorsConfiguration();
        defaultCorsConfig.addAllowedOrigin("https://www.acmicpc.net");
        defaultCorsConfig.addAllowedOrigin("https://school.programmers.co.kr");
        defaultCorsConfig.addAllowedOrigin("https://leetcode.com");
        defaultCorsConfig.setAllowedMethods(List.of("POST", "PATCH", "OPTIONS"));
        defaultCorsConfig.addAllowedHeader("*");
        defaultCorsConfig.setAllowCredentials(true);

        // 특정 URL에 대해 다른 CORS 정책을 적용하려면 URL 기반 CORS 매핑을 설정
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // 모든 경로에 기본 CORS 정책 적용
        source.registerCorsConfiguration("/api/v1/problem", defaultCorsConfig);

        return source;
    }


    private void defaultHttpSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
    }

}
