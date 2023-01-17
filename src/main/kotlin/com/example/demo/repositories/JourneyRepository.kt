package com.example.demo.repositories

import com.example.demo.entities.JourneyEntity
import com.example.demo.entities.UserEntity
import com.example.demo.types.Visibility
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JourneyRepository: JpaRepository<JourneyEntity, Long> {
    fun findJourneysByUserUsername(username: String): List<JourneyEntity>
    fun findAllByUserUsernameAndVisibilityIsIn(username: String, visibilities: List<Visibility>): List<JourneyEntity>
    fun findJourneysById(id: Long): JourneyEntity?
    fun findJourneyEntitiesByUserNot(user: UserEntity): List<JourneyEntity>
    fun findJourneyEntitiesByUserUsernameAndVisibility(username: String, visibility: Visibility): List<JourneyEntity>
}