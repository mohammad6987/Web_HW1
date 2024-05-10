package com.example.web_hw1.JWTUtils;

import com.example.web_hw1.Exception.ExpiredTokenException;
import com.example.web_hw1.Model.EndUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenManger {

    private static final String SECRET = "qwertty";
    public  String generateToken(String key,Date expire,String ownerUsername) {
        if(System.currentTimeMillis() >= expire.getTime()){
            throw new ExpiredTokenException("Provided expire time is in the past.");
        }
        return Jwts.builder()
                .setSubject(key)
                .setExpiration(expire)
                .claim("ownerUsername",ownerUsername )
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }
    public  boolean validateToken(String token ,String key, EndUser endUser){
        String tokenKey = extractKey(token);
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        boolean isExpired = claims.getExpiration().before(new Date());
        String extractedUsername = claims.get("ownerUsername" , String.class);
        return (endUser.getUsername().equals(extractedUsername) && !isExpired && tokenKey.equals(key));
    }
    public String extractKey(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
