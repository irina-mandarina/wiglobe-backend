package com.example.demo.services.serviceImplementations

import com.example.demo.requestEntities.JourneyRequest
import com.example.demo.services.JourneyService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class JourneyServiceImpl: JourneyService {
    override fun createJourney(username: String, journeyRequest: JourneyRequest): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun deleteJourney(username: String, journeyId: Long): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun editJourney(
        username: String,
        journeyRequest: JourneyRequest,
        journeyId: Long
    ): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun getJourney(username: String, journeyId: Long): ResponseEntity<String> {
        TODO("Not yet implemented")
    }
}