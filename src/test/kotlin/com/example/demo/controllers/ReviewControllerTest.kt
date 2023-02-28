package com.example.demo.controllers;

import com.example.demo.models.requestModels.ReviewRequest
import com.example.demo.models.responseModels.Review
import com.example.demo.services.ReviewService
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.http.ResponseEntity
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertSame

class ReviewControllerTest {

    private val reviewService = mockkClass(ReviewService::class)
    private val reviewController = ReviewController(reviewService)
    private val sampleUsername = "kiril"
    private val sampleDestinationId = 1L
    private val sampleReviewId = 1L

    @Test
    fun reviewServiceReturnsReviews_WhenGetReviewsForDestinationCalled_ReviewsReturned() {
        // GIVEN:
        every {
            reviewService.getReviewsForDestination(sampleUsername, sampleDestinationId)
        } returns ResponseEntity.ok().body(
            listOf(
                mockkClass(Review::class)
            )
        )

        // WHEN:
        val responseFromController = reviewController.getReviewsForDestination(sampleUsername, sampleDestinationId)

        // THEN:
        assertNotNull(responseFromController)
        assertSame(responseFromController, reviewService.getReviewsForDestination(sampleUsername, sampleDestinationId))
    }


    @Test
    fun reviewServiceReturnsReview_WhenReviewDestinationCalled_ReviewReturned() {
        // GIVEN:
        val sampleReviewReq = mockkClass(ReviewRequest::class)
        every {
            reviewService.reviewDestination(sampleUsername, sampleDestinationId, sampleReviewReq)
        } returns ResponseEntity.ok().body(
            mockkClass(Review::class)
        )

        // WHEN:
        val responseFromController = reviewController.reviewDestination(sampleUsername, sampleDestinationId, sampleReviewReq)

        // THEN:
        assertNotNull(responseFromController)
        assertSame(responseFromController, reviewService.reviewDestination(sampleUsername, sampleDestinationId, sampleReviewReq))
    }


    @Test
    fun reviewServiceReturnsReview_WhenEditReviewCalled_ReviewReturned() {
        // GIVEN:
        val sampleReviewReq = mockkClass(ReviewRequest::class)
        every {
            reviewService.editReview(sampleUsername, sampleReviewId, sampleReviewReq)
        } returns ResponseEntity.ok().body(
            mockkClass(Review::class)
        )

        // WHEN:
        val responseFromController = reviewController.editReview(sampleUsername, sampleReviewId, sampleReviewReq)

        // THEN:
        assertNotNull(responseFromController)
        assertSame(responseFromController, reviewService.editReview(sampleUsername, sampleReviewId, sampleReviewReq))
    }

    @Test
    fun reviewServiceReturnsBodyNull_WhenGetDeleteReviewCalled_BodyNullReturned() {
        // GIVEN:
        every {
            reviewService.deleteReview(sampleUsername, sampleReviewId)
        } returns ResponseEntity.ok().body(
            null
        )

        // WHEN:
        val responseFromController = reviewController
            .deleteReview(sampleUsername, sampleDestinationId, sampleReviewId)

        // THEN:
        assertNotNull(responseFromController)
        verify(exactly = 1) { reviewService.deleteReview(sampleUsername, sampleReviewId) }
        val result: ResponseEntity<String> = ResponseEntity.ok().body(null)
        assertEquals(responseFromController, result)
    }
}
