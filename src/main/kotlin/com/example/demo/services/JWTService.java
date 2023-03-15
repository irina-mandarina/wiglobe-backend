package com.example.demo.services;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier.Builder;
import java.util.Collections;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;


@Service
public class JWTService {
    @Value("${google.clientid}")
    private static String CLIENT_ID;
    private final SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
    private final Key key = Keys.secretKeyFor(algorithm);
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

    public String getIssuer(String token) {
        try {
            Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return jws.getBody().getIssuer();

        } catch (JwtException e) {
            //don't trust the JWT!
            throw e;
        }
    }

    public String getGoogleEmail(String token) throws Exception {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        GoogleIdToken googleIdToken = verifier.verify(token);
        if (googleIdToken != null) {
            GoogleIdToken.Payload payload = googleIdToken.getPayload();
            return payload.getEmail();
        }
        return null;
    }

    public boolean googleJWTIsValid(String idToken) throws Exception {
        GoogleIdTokenVerifier verifier = new Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        GoogleIdToken token = verifier.verify(idToken);
        return token != null;
    }
}
