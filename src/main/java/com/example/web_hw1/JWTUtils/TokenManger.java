package com.example.web_hw1.JWTUtils;

import com.example.web_hw1.Model.EndUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Timer;

@Component
public class TokenManger {
    private static final String SECRET = "qwertty";
    public static String generateToken(String username,long expire) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
    public static boolean validateToken(String token , EndUser endUser){
        String tokenUserName = extractUsername(token);
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        boolean isExpired = claims.getExpiration().before(new Date());
        return (endUser.getUsername().equals(tokenUserName) && !isExpired);
    }
    public static String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
