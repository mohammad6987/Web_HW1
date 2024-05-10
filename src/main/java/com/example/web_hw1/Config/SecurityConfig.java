package com.example.web_hw1.Config;

import com.example.web_hw1.Model.EndUser;
import com.example.web_hw1.Model.EndUserDto;
import com.example.web_hw1.Service.EndUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final EndUserDetailsService endUserDetailsService;

    public SecurityConfig(EndUserDetailsService endUserDetailsService) {
        this.endUserDetailsService = endUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
         httpSecurity.
                 csrf(csrf -> csrf.disable()).authorizeHttpRequests()
                 .requestMatchers("/users/login").permitAll()
                 .requestMatchers("/users/register").permitAll()
                 .requestMatchers("/admin/*").hasRole("ADMIN")
                 .anyRequest().permitAll()
                 .and().formLogin().loginPage("/users/login").defaultSuccessUrl("/countries" , true)

                  ;

         return httpSecurity.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        return new InMemoryUserDetailsManager(
                User.withUsername("test").
                        password("123").build());

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
}
