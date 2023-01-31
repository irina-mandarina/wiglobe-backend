package com.example.demo.repositories

import com.example.demo.entities.SessionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SessionRepository: JpaRepository<SessionEntity, Long> {
    fun findByUserUsername(username: String): SessionEntity?
}