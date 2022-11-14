package com.example.demo.controllers

import com.example.demo.requestEntities.ReviewRequest
import com.example.demo.services.ReviewService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ReviewController {
    private val reviewService: ReviewService = TODO()

    @GetMapping("/journeys/{journeyId}/reviews")
    fun getReviewsForJourney(@RequestHeader username: String, @PathVariable journeyId: Long
    ): ResponseEntity<String> {
        return reviewService.getReviewsForJourney(username, journeyId)
    }

    @PostMapping("/journeys/{journeyId}/reviews")
    fun reviewJourney(@RequestHeader username: String, @PathVariable journeyId: String,
                      @RequestBody reviewRequest: ReviewRequest): ResponseEntity<String> {
        return reviewService.reviewJourney(username, journeyId, reviewRequest)
    }

    @PutMapping("/journeys/{journeyId}/reviews/{reviewId}")
    fun editReview(@RequestHeader username: String, @PathVariable journeyId: Long,
                   @PathVariable reviewId: Long): ResponseEntity<String> {
        return reviewService.editReview(username, journeyId, reviewId)
    }

    @DeleteMapping("/journeys/{journeyId}/reviews/{reviewId}")
    fun deleteReview(@RequestHeader username: String, @PathVariable journeyId: Long,
                     @PathVariable reviewId: Long): ResponseEntity<String> {
        return reviewService.deleteReview(username, journeyId, reviewId)
    }
}