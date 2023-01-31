package com.example.demo.controllers

import com.example.demo.models.requestModels.ReviewRequest
import com.example.demo.models.responseModels.Review
import com.example.demo.services.ReviewService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("http://localhost:3000")
class ReviewController(private val reviewService: ReviewService) {
    @GetMapping("/destinations/{destinationId}/reviews")
    fun getReviewsForDestination(@RequestAttribute username: String,
                                 @PathVariable destinationId: Long): ResponseEntity<List<Review>> {
        return reviewService.getReviewsForDestination(username, destinationId)
    }

    @PostMapping("/destinations/{destinationId}/reviews")
    fun reviewDestination(@RequestAttribute username: String, @PathVariable destinationId: Long,
                      @RequestBody reviewRequest: ReviewRequest): ResponseEntity<Review> {
        return reviewService.reviewDestination(username, destinationId, reviewRequest)
    }

    @PutMapping("/destinations/{destinationId}/reviews/{reviewId}")
    fun editReview(@RequestAttribute username: String, @PathVariable reviewId: Long,
                   @RequestBody reviewRequest: ReviewRequest): ResponseEntity<Review> {
        return reviewService.editReview(username, reviewId, reviewRequest)
    }

    @DeleteMapping("/destinations/{destinationId}/reviews/{reviewId}")
    fun deleteReview(@RequestAttribute username: String, @PathVariable destinationId: Long,
                     @PathVariable reviewId: Long): ResponseEntity<String> {
        return reviewService.deleteReview(username, reviewId)
    }
}