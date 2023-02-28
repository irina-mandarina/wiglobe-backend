package com.example.demo.controllers

import com.example.demo.entities.JourneyEntity
import com.example.demo.models.requestModels.JourneyRequest
import com.example.demo.models.responseModels.Journey
import com.example.demo.recommender.JourneyRecommender
import com.example.demo.services.JourneyService
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.http.ResponseEntity
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertSame

class JourneyControllerTest {
    private val journeyService = mockkClass(JourneyService::class)
    private val journeyRecommender = mockkClass(JourneyRecommender::class)
    private val journeyController = JourneyController(journeyService, journeyRecommender)
    private val sampleUsername = "kiril"
    private val sampleJourneyId = 1L
    @Test
    fun journeyServiceReturnsJourney_WhenCreateJourneyCalled_JourneyReturned() {
        // GIVEN:
        val sampleJourneyReq = mockkClass(JourneyRequest::class)
        every {
            journeyService.createJourney(sampleUsername, sampleJourneyReq)
        } returns ResponseEntity.ok().body(
            mockkClass(Journey::class)
        )

        // WHEN:
        val responseFromController = journeyController.createJourney(sampleUsername, sampleJourneyReq)

        // THEN:
        assertNotNull(responseFromController)
        assertSame(responseFromController, journeyService.createJourney(sampleUsername, sampleJourneyReq))
    }

    @Test
    fun journeyServiceReturnsBodyNull_WhenDeleteJourneyCalled_BodyNullReturned() {
        // GIVEN:
        every {
            journeyService.deleteJourney(sampleUsername, sampleJourneyId)
        } returns ResponseEntity.ok().body(
            null
        )

        // WHEN:
        val responseFromController = journeyController.deleteJourney(sampleUsername, sampleJourneyId)

        // THEN:
        verify(exactly = 1) { journeyService.deleteJourney(sampleUsername, sampleJourneyId) }
        assertNotNull(responseFromController)
        val result: ResponseEntity<String> = ResponseEntity.ok().body(null)
        assertSame(responseFromController, journeyService.deleteJourney(sampleUsername, sampleJourneyId))
        assertEquals(responseFromController, result)
    }

    @Test
    fun journeyServiceReturnsJourney_WhenEditJourneyCalled_JourneyReturned() {
        // GIVEN:
        val sampleJourneyReq = mockkClass(JourneyRequest::class)
        every {
            journeyService.editJourney(sampleUsername, sampleJourneyId, sampleJourneyReq)
        } returns ResponseEntity.ok().body(
            mockkClass(Journey::class)
        )

        // WHEN:
        val responseFromController = journeyController.editJourney(sampleUsername, sampleJourneyId, sampleJourneyReq)

        // THEN:
        assertNotNull(responseFromController)
        assertSame(responseFromController, journeyService.editJourney(sampleUsername, sampleJourneyId, sampleJourneyReq))
    }

    @Test
    fun journeyServiceReturnsJourney_WhenGetJourneyCalled_JourneyReturned() {
        // GIVEN:
        every {
            journeyService.getJourney(sampleUsername, sampleJourneyId)
        } returns ResponseEntity.ok().body(
            mockkClass(Journey::class)
        )

        // WHEN:
        val responseFromController = journeyController.getJourney(sampleUsername, sampleJourneyId)

        // THEN:
        assertNotNull(responseFromController)
        assertSame(responseFromController, journeyService.getJourney(sampleUsername, sampleJourneyId))
    }


    @Test
    fun journeyServiceReturnsJourneys_WhenGetJourneyRecommendationsCalled_JourneysReturned() {
        // GIVEN:
        every {
            journeyRecommender.recommendJourneysToUser(sampleUsername)
        } returns mapOf(
            Pair(mockkClass(JourneyEntity::class),
            0.7)
        )

        // WHEN:
        val responseFromController = journeyRecommender.recommendJourneysToUser(sampleUsername)

        // THEN:
        assertNotNull(responseFromController)
        assertSame(responseFromController, journeyRecommender.recommendJourneysToUser(sampleUsername))
    }


    @Test
    fun journeyServiceReturnsJourneys_WhenGetJourneysByUserCalled_JourneysReturned() {
        // GIVEN:
        val sampleJourneyReq = mockkClass(JourneyRequest::class)
        every {
            journeyService.getJourneysByUser(sampleUsername, sampleUsername)
        } returns ResponseEntity.ok().body(
            listOf(
                mockkClass(Journey::class)
            )
        )

        // WHEN:
        val responseFromController = journeyController.getJourneysByUser(sampleUsername, sampleUsername)

        // THEN:
        assertNotNull(responseFromController)
        assertSame(responseFromController, journeyService.getJourneysByUser(sampleUsername, sampleUsername))
    }


    @Test
    fun journeyServiceReturnsJourneys_WhenGetDraftsCalled_JourneysReturned() {
        // GIVEN:
        val sampleJourneyReq = mockkClass(JourneyRequest::class)
        every {
            journeyService.getDrafts(sampleUsername)
        } returns ResponseEntity.ok().body(
            listOf(
                mockkClass(Journey::class)
            )
        )

        // WHEN:
        val responseFromController = journeyController.getDrafts(sampleUsername)

        // THEN:
        assertNotNull(responseFromController)
        assertSame(responseFromController, journeyService.getDrafts(sampleUsername))
    }

    @Test
    fun journeyServiceReturnsJourneys_WhenGetJourneysByDestinationCalled_JourneysReturned() {
        // GIVEN:
        val sampleDestinationId = 100L
        every {
            journeyService.getJourneysByDestination(sampleUsername, sampleDestinationId)
        } returns ResponseEntity.ok().body(
            listOf(
                mockkClass(Journey::class)
            )
        )

        // WHEN:
        val responseFromController = journeyController.getJourneysByDestination(sampleUsername, sampleDestinationId)

        // THEN:
        assertNotNull(responseFromController)
        assertSame(responseFromController, journeyService.getJourneysByDestination(sampleUsername, sampleDestinationId))
    }
}