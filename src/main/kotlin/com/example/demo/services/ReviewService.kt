package com.example.demo.services

import com.example.demo.requestEntities.ReviewRequest
import org.springframework.http.ResponseEntity

interface ReviewService {
    fun getReviewsForJourney(username: String, journeyId: Long): ResponseEntity<String>

    fun reviewJourney(username: String, journeyId: String,
                      reviewRequest: ReviewRequest): ResponseEntity<String>

    fun editReview(username: String, journeyId: Long, reviewId: Long): ResponseEntity<String>

    fun deleteReview(username: String, journeyId: Long, reviewId: Long): ResponseEntity<String>
}