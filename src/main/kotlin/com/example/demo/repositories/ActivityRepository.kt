package com.example.demo.repositories

import com.example.demo.entities.ActivityEntity
import com.example.demo.entities.JourneyEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ActivityRepository: JpaRepository<ActivityEntity, Long> {
    fun findActivityById(id: Long): ActivityEntity?
    fun findActivitiesByJourney(journey: JourneyEntity): List<ActivityEntity>
}