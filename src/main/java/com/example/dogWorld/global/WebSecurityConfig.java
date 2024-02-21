package com.example.dogWorld.global;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final CorsConfig corsConfig;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilter(corsConfig.corsFilter())
//                .formLogin(FormLoginConfigurer::disable)
//                .logout(LogoutConfigurer::disable)
                .authorizeHttpRequests(authHttp -> authHttp
                .requestMatchers("/no-auth","/auth/signup" ,"/auth/login","/auth/me", "/auth/**", "/api/**", "/posts/**")
                .permitAll()
                .requestMatchers("/re-auth" )
                .authenticated()
                .requestMatchers("/")
                .anonymous()
        );
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
