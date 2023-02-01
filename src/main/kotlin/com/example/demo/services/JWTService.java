package com.example.demo.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Service
public class JWTService {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public String encode(String username) {
        Date exp = new Date();
        if (exp.getMonth() == 12) {
            exp.setMonth(1);
        }
        else
            exp.setMonth(exp.getMonth()+1);

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(exp)
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

    public boolean JWTisValid(String token) {
        try {
            return getExp(token).after(new Date());
        }
        catch (JwtException e) {
            return false;
        }
    }
}
