package com.example.demo.repositories

import com.example.demo.entities.JourneyEntity
import com.example.demo.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JourneyRepository: JpaRepository<JourneyEntity, Long> {
    fun findJourneysByUser(user: UserEntity): List<JourneyEntity>

    fun findJourneysById(id: Long): JourneyEntity?
    fun findJourneyEntitiesByUserNot(user: UserEntity): List<JourneyEntity>
}