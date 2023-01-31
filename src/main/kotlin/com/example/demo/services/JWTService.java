package com.example.demo.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JWTService {
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public String encode(String username) {

        return Jwts.builder().setSubject(username).signWith(key).compact();
    }

    public String getSubject(String token) throws JwtException {
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
