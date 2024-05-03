package com.example.web_hw1.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.
                csrf(csrf -> csrf.disable()).
                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.NEVER)).
                authorizeHttpRequests(auth -> auth.anyRequest().permitAll()).build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        return new InMemoryUserDetailsManager(
                User.withUsername("test").
                        password("123").build()
        );
    }
    
}
