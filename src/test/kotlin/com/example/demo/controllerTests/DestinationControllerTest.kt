package com.example.demo.controllerTests

import com.example.demo.controllers.ActivityController
import com.example.demo.controllers.DestinationController
import com.example.demo.models.responseModels.Destination
import com.example.demo.models.responseModels.DestinationSearchResult
import com.example.demo.services.DestinationService
import io.mockk.every
import io.mockk.mockkClass
import org.junit.jupiter.api.Test
import org.springframework.http.ResponseEntity
import kotlin.test.assertNotNull
import kotlin.test.assertSame


class DestinationControllerTest {

    val destinationService = mockkClass(DestinationService::class)
    val destinationController = DestinationController(destinationService)
    val sampleKeyword = "dunav"
    val sampleDestinationId = 1L

    @Test
    fun destinationServiceReturnsDestination_WhenGetDestinationCalled_DestinationReturned() {
        // GIVEN
        every {
            destinationService.getDestination(sampleDestinationId)
        } returns ResponseEntity.ok().body(
            mockkClass(Destination::class)
        )
        // WHEN
        val responseFromController = destinationController.getDestination(sampleDestinationId)

        // THEN
        assertNotNull(responseFromController)
        assertSame(responseFromController, destinationService.getDestination(sampleDestinationId))

    }

    @Test
    fun destinationServiceReturnsDestination_WhenSearchDestinationCalled_DestinationReturned() {
        // GIVEN
        every {
            destinationService.searchDestinations(sampleKeyword)
        } returns ResponseEntity.ok().body(
            listOf(
                mockkClass(DestinationSearchResult::class)
            )
        )
        // WHEN
        val responseFromController = destinationController.searchDestinations(sampleKeyword)

        // THEN
        assertNotNull(responseFromController)
        assertSame(responseFromController, destinationService.searchDestinations(sampleKeyword))

    }

}