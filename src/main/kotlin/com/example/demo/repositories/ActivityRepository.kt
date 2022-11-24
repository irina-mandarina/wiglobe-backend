package com.example.demo.repositories

import com.example.demo.entities.Activity
import com.example.demo.entities.Journey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ActivityRepository: JpaRepository<Activity, Long> {
    fun findActivityById(id: Long): Activity?

    fun findActivitiesByJourney(journey: Journey): List<Activity>
}