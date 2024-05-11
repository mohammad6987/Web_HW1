package com.example.web_hw1.JWTUtils;

import com.example.web_hw1.Model.EndUser;
import com.example.web_hw1.Service.EndUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.Collections;
import java.util.List;

@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    private TokenManger tokenManger;
    @Autowired
    private EndUserDetailsService endUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            String header = request.getHeader("Authorization");
            if(header == null || !header.startsWith("API") ){
                filterChain.doFilter(request, response);
                return;
            }
            System.out.println(header);
            String token = header.substring(4);
            String username = tokenManger.extractKey(token);
            try {
                EndUser endUser = endUserDetailsService.getUserByUsername(username);
                if(!tokenManger.validateToken(token, endUser)){
                    filterChain.doFilter(request, response);
                    return;
                }
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        endUser, null , endUser==null ? List.of() : List.of(endUser.getAuthority())
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);

            }catch (Exception e){
                filterChain.doFilter(request,response);
                return;
            }


    }
}
