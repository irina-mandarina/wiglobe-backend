package com.example.demo.services.serviceImplementations

import com.example.demo.requestEntities.ReviewRequest
import com.example.demo.services.ReviewService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ReviewServiceImpl: ReviewService {
    override fun getReviewsForJourney(username: String, journeyId: Long): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun reviewJourney(
        username: String,
        journeyId: String,
        reviewRequest: ReviewRequest
    ): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun editReview(username: String, journeyId: Long, reviewId: Long): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun deleteReview(username: String, journeyId: Long, reviewId: Long): ResponseEntity<String> {
        TODO("Not yet implemented")
    }
}