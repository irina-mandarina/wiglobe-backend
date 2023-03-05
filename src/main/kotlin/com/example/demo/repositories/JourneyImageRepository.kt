package com.example.demo.repositories

import com.example.demo.entities.JourneyEntity
import com.example.demo.entities.JourneyImageEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JourneyImageRepository: JpaRepository<JourneyImageEntity, Long> {
    fun findAllByJourney(journeyEntity: JourneyEntity): List<JourneyImageEntity>
}