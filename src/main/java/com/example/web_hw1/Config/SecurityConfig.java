package com.example.web_hw1.Config;

import com.example.web_hw1.JWTUtils.JWTFilter;
import com.example.web_hw1.Model.EndUser;
import com.example.web_hw1.Model.EndUserDto;
import com.example.web_hw1.Model.TokenPack;
import com.example.web_hw1.Service.EndUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final EndUserDetailsService endUserDetailsService;
    private final JWTFilter jwtFilter;
    @Value("${name}")
    String admin_username;
    @Value("${password}")
    String admin_password;

    public SecurityConfig(EndUserDetailsService endUserDetailsService,JWTFilter jwtFilter) {
        this.endUserDetailsService = endUserDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
         httpSecurity.
                 csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth ->auth
                         .requestMatchers("/users/register").permitAll()
                         .requestMatchers("/users/login").permitAll()
                         //.requestMatchers(HttpMethod.GET,"/user/api-tokens" ).permitAll()
                         .requestMatchers("/admin/*").hasAuthority("ROLE_ADMIN")
                         .anyRequest().authenticated()
                 )
                 .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                 .sessionManagement(sessionMgmt -> sessionMgmt.sessionCreationPolicy(SessionCreationPolicy.STATELESS))


                 ;



         return httpSecurity.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() throws Exception {

        EndUser admin = endUserDetailsService.getEndUserRepository().getEndUserByUsername(admin_username).orElse(null);
        if(admin == null || admin.getRole().equals("USER")){
        admin = new EndUser();
        admin.setAuthority(new SimpleGrantedAuthority("ROLE_ADMIN"));
        admin.setRole("ADMIN");
        admin.setUsername(admin_username);
        admin.setPassword(endUserDetailsService.hashString(admin_password));
        admin.setAuthorized(true);
        admin.setId(0L);
        endUserDetailsService.getEndUserRepository().save(admin);
        TokenPack tokenPack = new TokenPack();
        tokenPack.setName("prime_token");
        tokenPack.setExpireDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse("2029-01-01T23:59:59Z"));
        tokenPack.setOwnerUsername(admin.getUsername());
        tokenPack.setTokenValue(endUserDetailsService.generateToken("prime_token","2029-01-01T23:59:59Z" , admin).getTokenValue());
        endUserDetailsService.getTokenRepository().save(tokenPack);
        }
        return new InMemoryUserDetailsManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
