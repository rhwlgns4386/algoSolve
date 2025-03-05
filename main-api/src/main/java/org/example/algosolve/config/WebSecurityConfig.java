package org.example.algosolve.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.algosolve.user.domain.UserRepository;
import org.example.algosolve.user.security.filter.JwtAuthenticationExceptionProvider;
import org.example.algosolve.user.security.filter.JwtAuthenticationFilter;
import org.example.algosolve.user.security.filter.JwtAuthenticationProvider;
import org.example.algosolve.user.security.filter.JwtAuthenticationProviderImpl;
import org.example.algosolve.user.security.service.JpaUserDetailService;
import org.example.algosolve.user.token.TokenProvider;
import org.example.algosolve.user.token.TokenType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig implements WebMvcConfigurer {

    private final TokenProvider provider;
    private final ObjectMapper objectMapper;

    @Bean
    @Order(4)
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        defaultHttpSecurity(httpSecurity);

        httpSecurity.authorizeHttpRequests(
                        (auth) -> auth.anyRequest().authenticated())
                .addFilterAfter(jwtExceptionHandleFilter(TokenType.ACCESS_TOKEN), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    @Order(3)
    public SecurityFilterChain securityAuthFilterChain(HttpSecurity httpSecurity) throws Exception {
        defaultHttpSecurity(httpSecurity);

        httpSecurity.securityMatcher("/auth/**", "/swagger-ui/**", "/v3/api-docs/**");
        httpSecurity.authorizeHttpRequests(
                (auth) -> auth.anyRequest().permitAll());
        return httpSecurity.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain refreshTokenCheckChain(HttpSecurity httpSecurity) throws Exception {
        defaultHttpSecurity(httpSecurity);

        httpSecurity.securityMatcher("/auth/issued_access_token");
        httpSecurity.authorizeHttpRequests(
                        (auth) -> auth.anyRequest().authenticated())
                .addFilterAfter(jwtExceptionHandleFilter(TokenType.REFRESH_TOKEN), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    private JwtAuthenticationFilter jwtExceptionHandleFilter(TokenType tokenType) {
        JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationExceptionProvider(new JwtAuthenticationProviderImpl(provider, tokenType));
        return new JwtAuthenticationFilter(objectMapper, jwtAuthenticationProvider);
    }

    @Bean
    @Order(1)
    @ConditionalOnProperty(name = "spring.h2.console.enabled", havingValue = "true")
    public SecurityFilterChain h2securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        defaultHttpSecurity(httpSecurity);

        httpSecurity.securityMatcher(PathRequest.toH2Console());
        httpSecurity.authorizeHttpRequests(
                        (auth) -> auth.requestMatchers(PathRequest.toH2Console()).permitAll())
                .csrf((csrf) -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()))
                .headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin));
        return httpSecurity.build();
    }

    private void defaultHttpSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .cors((httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(defaultCors())))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
    }

    @Bean
    public CorsConfigurationSource defaultCors() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("http://localhost:3000");  // 허용할 오리진(출처)
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));  // 허용할 HTTP 메소드
        corsConfig.addAllowedHeader("*");  // 모든 헤더 허용
        corsConfig.setAllowCredentials(true);  // 자격 증명(Credentials) 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);  // 모든 경로에 CORS 적용

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new JpaUserDetailService(userRepository);
    }
}
