package com.example.web_hw1.JWTUtils;

import com.example.web_hw1.Exception.ExpiredTokenException;
import com.example.web_hw1.Model.EndUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenManger {
    //@Value("${SECRET}")
    private static final String SECRET  = "123456789";
    public  String generateToken(String key,Date expire) {
        if(System.currentTimeMillis() >= expire.getTime()){
            throw new ExpiredTokenException("Provided expire time is in the past.");
        }
        return "API "+Jwts.builder()
                .setSubject(key)
                .setExpiration(expire)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
    public  boolean validateToken(String token , EndUser endUser){
        String tokenKey = extractKey(token);
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        boolean isExpired = claims.getExpiration().before(new Date());
        return (!isExpired && tokenKey.equals(endUser.getUsername()));
    }
    public String extractKey(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace("API ", ""))
                .getBody()
                .getSubject();
    }

}
