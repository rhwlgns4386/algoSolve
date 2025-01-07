package org.example.algosolve.config;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.algosolve.user.domain.UserRepository;
import org.example.algosolve.user.security.ExceptionHandleFilter;
import org.example.algosolve.user.security.JpaUserDetailService;
import org.example.algosolve.user.security.JwtAuthenticationFilter;
import org.example.algosolve.user.token.TokenProvider;
import org.example.algosolve.user.token.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig implements WebMvcConfigurer{

    private final TokenProvider provider;

    @Bean
    @Order(4)
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        defaultHttpSecurity(httpSecurity);

        httpSecurity.authorizeHttpRequests(
                        (auth) -> auth.anyRequest().authenticated())
                .addFilterAfter(new JwtAuthenticationFilter(provider, TokenType.ACCESS_TOKEN), ExceptionHandleFilter.class);
        return httpSecurity.build();
    }

    @Bean
    @Order(3)
    public SecurityFilterChain securityAuthFilterChain(HttpSecurity httpSecurity) throws Exception {
        defaultHttpSecurity(httpSecurity);

        httpSecurity.securityMatcher("/auth/**");
        httpSecurity.authorizeHttpRequests(
                (auth) -> auth.requestMatchers("/auth/**")
                        .permitAll().anyRequest().authenticated());
        return httpSecurity.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain refreshTokenCheckChain(HttpSecurity httpSecurity) throws Exception {
        defaultHttpSecurity(httpSecurity);

        httpSecurity.securityMatcher("/auth/issued_access_token");
        httpSecurity.authorizeHttpRequests(
                        (auth) -> auth.anyRequest().authenticated())
                .addFilterAfter(new JwtAuthenticationFilter(provider,TokenType.REFRESH_TOKEN), ExceptionHandleFilter.class);
        return httpSecurity.build();
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

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setExposedHeaders(List.of("*"));
        config.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Autowired
    private final ObjectMapper objectMapper;
    private void defaultHttpSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        httpSecurity.addFilterBefore(new ExceptionHandleFilter(objectMapper),UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE","PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
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
