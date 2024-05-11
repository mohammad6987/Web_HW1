package com.example.web_hw1.Config;

import com.example.web_hw1.JWTUtils.JWTFilter;
import com.example.web_hw1.Model.EndUser;
import com.example.web_hw1.Model.EndUserDto;
import com.example.web_hw1.Service.EndUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final EndUserDetailsService endUserDetailsService;
    private final JWTFilter jwtFilter;

    public SecurityConfig(EndUserDetailsService endUserDetailsService,JWTFilter jwtFilter) {
        this.endUserDetailsService = endUserDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
         httpSecurity.
                 csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth ->auth
                         //.requestMatchers("/users/login").permitAll()
                         .requestMatchers("/users/register").permitAll()
                         .requestMatchers("/users/login").permitAll()
                         .requestMatchers("/user/api-tokens").permitAll()
                         .requestMatchers("/admin/*").hasAuthority("ROLE_ADMIN")
                         .anyRequest().authenticated()
                 )
                 .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                 .sessionManagement(sessionMgmt -> sessionMgmt.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                  ;

         return httpSecurity.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() throws Exception {
        EndUser admin = new EndUser();
        admin.setAuthority(new SimpleGrantedAuthority("ROLE_ADMIN"));
        admin.setRole("ADMIN");
        admin.setUsername("test");
        admin.setPassword(endUserDetailsService.hashString("123"));
        admin.setAuthorized(true);
        admin.setId(-1L);
        admin.setUsername("admin");
        endUserDetailsService.getEndUserRepository().save(admin);
        return new InMemoryUserDetailsManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
}
