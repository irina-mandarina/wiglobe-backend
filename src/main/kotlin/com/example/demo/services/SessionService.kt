package com.example.demo.services

import com.example.demo.entities.SessionEntity
import com.example.demo.repositories.SessionRepository
import io.jsonwebtoken.JwtException
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.Date

@Service
class SessionService(private val sessionRepository: SessionRepository, private val jwtService: JWTService) {
    fun findByUserUsername(username: String): SessionEntity? {
        return sessionRepository.findByUserUsername(username)
    }

    fun issueJWT(username: String): String {
        return jwtService.encode(username)
    }

    fun JWTisValid(token: String): Boolean {
        try {
            val exp: Date = jwtService.getExp(token)
            return exp.toInstant().isAfter(Instant.now())

        }
        catch (e: JwtException) {
            return false
        }
    }

    fun getUsernameFromJWT(token: String): String {
        return jwtService.getSubject(token)
    }

}