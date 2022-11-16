package com.example.demo.controllers

import com.example.demo.requestEntities.ReviewRequest
import com.example.demo.services.ReviewService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ReviewController(val reviewService: ReviewService) {
    @GetMapping("/destinations/{destinationId}/reviews")
    fun getReviewsForDestination(@RequestHeader username: String, @PathVariable destinationId: Long
    ): ResponseEntity<String> {
        return reviewService.getReviewsForDestination(username, destinationId)
    }

    @PostMapping("/destinations/{destinationId}/reviews")
    fun reviewdestination(@RequestHeader username: String, @PathVariable destinationId: Long,
                      @RequestBody reviewRequest: ReviewRequest): ResponseEntity<String> {
        reviewRequest.destinationId = destinationId
        return reviewService.reviewDestination(username, reviewRequest)
    }

    @PutMapping("/destinations/{destinationId}/reviews/{reviewId}")
    fun editReview(@RequestHeader username: String, @PathVariable destinationId: Long,
                   @PathVariable reviewId: Long, reviewRequest: ReviewRequest): ResponseEntity<String> {
        reviewRequest.id = reviewId
        return reviewService.editReview(username, reviewRequest)
    }

    @DeleteMapping("/destinations/{destinationId}/reviews/{reviewId}")
    fun deleteReview(@RequestHeader username: String, @PathVariable destinationId: Long,
                     @PathVariable reviewId: Long): ResponseEntity<String> {
        return reviewService.deleteReview(username, reviewId)
    }
}