package com.example.demo.services

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.Key
import java.util.Date
import java.sql.Timestamp
import java.time.LocalDateTime

@Service
class JWTService {
    private val algorithm = SignatureAlgorithm.HS256
    private val key: Key = Keys.secretKeyFor(algorithm)
    @Value("\${google.clientid}")
    private val CLIENT_ID: String? = null
    fun encode(username: String?): String {
        val exp = Timestamp.valueOf(LocalDateTime.now())
        if (exp.month == 12) {
            exp.month = 1
        } else exp.month = exp.month + 1
        return Jwts.builder()
            .setSubject(username)
            .setExpiration(exp)
            .signWith(key).compact()
    }

    @Throws(JwtException::class)
    fun getSubject(token: String?): String {
        return try {
            val jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            jws.body.subject

            //OK, we can trust this JWT
        } catch (e: JwtException) {
            //don't trust the JWT!
            throw e
        }
    }

    @Throws(JwtException::class)
    fun getExp(token: String?): Date {
        return try {
            val jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            jws.body.expiration

            //OK, we can trust this JWT
        } catch (e: JwtException) {

            //don't trust the JWT!
            throw e
        }
    }

    fun JWTisValid(token: String?): Boolean {
        return try {
            getExp(token).after(Date())
        } catch (e: JwtException) {
            false
        }
    }

    fun getIssuer(token: String?): String {
        return try {
            val jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            jws.body.issuer
        } catch (e: JwtException) {
            //don't trust the JWT!
            throw e
        }
    }

    @Throws(Exception::class)
    fun getGoogleEmail(token: String?): String? {
        val verifier = GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory())
            .setAudience(listOf(CLIENT_ID))
            .build()
        val googleIdToken = verifier.verify(token)
        if (googleIdToken != null) {
            val payload = googleIdToken.payload
            return payload.email
        }
        return null
    }

    @Throws(Exception::class)
    fun googleJWTIsValid(idToken: String?): Boolean {
        val verifier = GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory())
            .setAudience(listOf(CLIENT_ID))
            .build()
        val token = verifier.verify(idToken)
        return token != null
    }

}