package com.fooddelivery.delivery.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // ✅ Tắt CSRF để JS gọi API không lỗi
                .csrf(csrf -> csrf.disable())

                // ✅ Cho phép tất cả request, kể cả /admin, /restaurant, /api...
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )

                // ✅ Tắt hoàn toàn form login và logout của Spring Security
                .formLogin(form -> form.disable())
                .logout(logout -> logout.disable());

        return http.build();
    }
}
