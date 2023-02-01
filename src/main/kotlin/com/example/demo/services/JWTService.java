package com.example.demo.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class JWTService {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//    SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode("secretStringsecretStringsecretStringsecretStringsecretStringsecretStringsecretString65u75675r"));
    public String encode(String username) {
        Date now = new Date();
        if (now.getMonth() == 12) {
            now.setMonth(1);
        }
        else
            now.setMonth(now.getMonth()+1);

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(now)
                .signWith(key).compact();
    }

    public String getSubject(String token) throws JwtException {
        System.out.println(token);
        try {

            Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return jws.getBody().getSubject();

            //OK, we can trust this JWT

        } catch (JwtException e) {
            //don't trust the JWT!
            throw e;
        }
    }

    public Date getExp(String token) throws JwtException {
        try {

            Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return jws.getBody().getExpiration();

            //OK, we can trust this JWT

        } catch (JwtException e) {

            //don't trust the JWT!
            throw e;
        }
    }
}
