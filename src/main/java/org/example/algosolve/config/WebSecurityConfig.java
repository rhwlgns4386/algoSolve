package org.example.algosolve.config;

import lombok.RequiredArgsConstructor;
import org.example.algosolve.user.domain.UserRepository;
import org.example.algosolve.user.security.JpaUserDetailService;
import org.example.algosolve.user.security.JwtAuthenticationFilter;
import org.example.algosolve.user.token.TokenProvider;
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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final TokenProvider provider;

    @Bean
    @Order(3)
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        defaultHttpSecurity(httpSecurity);

        httpSecurity.authorizeHttpRequests(
                        (auth) -> auth.anyRequest().authenticated())
                .addFilterBefore(new JwtAuthenticationFilter(provider), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityAuthFilterChain(HttpSecurity httpSecurity) throws Exception {
        defaultHttpSecurity(httpSecurity);

        httpSecurity.securityMatcher("/auth/**");
        httpSecurity.authorizeHttpRequests(
                (auth) -> auth.requestMatchers("/auth/**")
                        .permitAll().anyRequest().authenticated());
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
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowedMethods("*");
            }
        };
    }
    private static void defaultHttpSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );;
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
