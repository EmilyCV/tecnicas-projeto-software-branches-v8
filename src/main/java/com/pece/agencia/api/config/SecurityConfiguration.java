package com.pece.agencia.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
                .requestMatchers("/swagger-ui.html").permitAll() // Example: Public endpoints
                .requestMatchers("/swagger-ui/**").permitAll() // Example: Public endpoints
                .requestMatchers("/v3/api-docs/**").permitAll() // Example: Public endpoints
                .requestMatchers("/v3/api-docs").permitAll() // Example: Public endpoints
                .requestMatchers("/swagger/**").permitAll()
                .requestMatchers("/actuator/**").permitAll() // Allow actuator endpoints
                .anyRequest().authenticated() // All other requests require authentication
        ).oauth2ResourceServer(oauth2ResourceServer ->
            oauth2ResourceServer
                .jwt(jwt -> {}) // Configure JWT validation
        ).csrf(x -> x.disable());
        return http.build();
    }
}
