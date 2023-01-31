package com.example.demo.services

import com.example.demo.entities.SessionEntity
import com.example.demo.repositories.SessionRepository
import org.springframework.stereotype.Service

@Service
class SessionService(private val sessionRepository: SessionRepository) {
    fun findByUserUsername(username: String): SessionEntity? {
        return sessionRepository.findByUserUsername(username)
    }

    fun issueJWT(username: String): String {
        val sessionEntity = findByUserUsername(username)
        return sessionEntity!!.user.username
    }

    fun JWTisValid(token: String): Boolean {
        return token.isNotEmpty()
    }

}