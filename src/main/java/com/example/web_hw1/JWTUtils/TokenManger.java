package com.example.web_hw1.JWTUtils;

import com.example.web_hw1.Exception.ExpiredTokenException;
import com.example.web_hw1.Model.EndUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Timer;

@Component
public class TokenManger {
    private static final String SECRET = "qwertty";
    public static String generateToken(String username,Date expire) {
        if(System.currentTimeMillis() >= expire.getTime()){
            throw new ExpiredTokenException("Provided expire time is in the past.");
        }
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expire)
                .signWith(SignatureAlgorithm.HS256, SECRET)
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
