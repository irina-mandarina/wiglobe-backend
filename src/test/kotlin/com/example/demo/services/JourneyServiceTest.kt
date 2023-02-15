package com.example.demo.services

import com.example.demo.entities.JourneyEntity
import com.example.demo.repositories.JourneyRepository
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.http.ResponseEntity
import kotlin.test.assertEquals


class JourneyServiceTest {
    val journeyRepository = mockkClass(JourneyRepository::class)
    val userService = mockkClass(UserService::class)
    val destinationService = mockkClass(DestinationService::class)
    val followService = mockkClass(FollowService::class)
    val journey = mockkClass(JourneyEntity::class)

    val journeyService = JourneyService(journeyRepository, userService, destinationService, followService)

    val username = "kiril"

    @Test
    fun journeyServiceDeletesJourney_WhenDeleteJourneyCalled_CommentDeleted() {
        // GIVEN:
        every {
            journeyRepository.delete(journey)
        } returns Unit

        every {
            journeyService.findJourneyById(2L)
        } returns journey

        // WHEN:
        journeyService.deleteJourney(username, 2L)

        // THEN:
        verify(exactly = 1) { journeyRepository.delete(journey) }
        verify(exactly = 1) { journeyRepository.findJourneyEntityById(2L) }

    }
}