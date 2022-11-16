package com.example.demo.services

import com.example.demo.entities.Destination
import com.example.demo.entities.Review
import com.example.demo.entities.User
import com.example.demo.requestEntities.ReviewRequest
import org.springframework.http.ResponseEntity

interface ReviewService {
    fun reviewWithIdExists(id: Long): Boolean
    fun findReviewById(reviewId: Long): Review?
    fun findReviewsByUser(user: User): List<Review>
    fun findReviewsByDestination(destination: Destination): List<Review>
    fun reviewDestination(username: String?, reviewRequest: ReviewRequest): ResponseEntity<String>
    fun getReviewsForDestination(username: String?, destinationId: Long?): ResponseEntity<String>
    fun deleteReview(username: String?, reviewId: Long?): ResponseEntity<String>
    fun editReview(username: String?, reviewRequest: ReviewRequest): ResponseEntity<String>
}