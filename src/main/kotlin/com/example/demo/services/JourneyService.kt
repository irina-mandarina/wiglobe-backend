package com.example.demo.services

import com.example.demo.requestEntities.JourneyRequest
import org.springframework.http.ResponseEntity

interface JourneyService {
    fun createJourney(username: String, journeyRequest: JourneyRequest): ResponseEntity<String>

    fun deleteJourney( username: String, journeyId: Long): ResponseEntity<String>

    fun editJourney(username: String, journeyRequest: JourneyRequest,
                    journeyId: Long
    ): ResponseEntity<String>

    fun getJourney( username: String, journeyId: Long): ResponseEntity<String>
}