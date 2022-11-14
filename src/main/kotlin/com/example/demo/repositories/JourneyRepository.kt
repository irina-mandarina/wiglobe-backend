package com.example.demo.repositories

import com.example.demo.entities.Journey
import com.example.demo.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JourneyRepository: JpaRepository<Journey, Long> {
    fun findJourneysByUser(user: User): List<Journey>
}