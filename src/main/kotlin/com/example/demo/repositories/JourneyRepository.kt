package com.example.demo.repositories

import com.example.demo.entities.JourneyEntity
import com.example.demo.entities.UserEntity
import com.example.demo.types.Visibility
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JourneyRepository: JpaRepository<JourneyEntity, Long> {
    fun findJourneysByUser(user: UserEntity): List<JourneyEntity>
    fun findAllByUserAndVisibility(user: UserEntity, visibility: Visibility): List<JourneyEntity>
    fun findAllByUserAndVisibilityIsIn(user: UserEntity, visibilities: List<Visibility>): List<JourneyEntity>
    fun findJourneysById(id: Long): JourneyEntity?
    fun findJourneyEntitiesByUserNot(user: UserEntity): List<JourneyEntity>
}