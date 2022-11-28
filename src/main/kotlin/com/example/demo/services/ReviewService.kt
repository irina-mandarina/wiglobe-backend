package com.example.demo.services

import com.example.demo.entities.Destination
import com.example.demo.entities.Review
import com.example.demo.entities.User
import com.example.demo.repositories.ReviewRepository
import com.example.demo.models.responseModels.ReviewResponse
import com.example.demo.models.PostReview
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ReviewService(val destinationService: DestinationService, val userService: UserService,
                    val reviewRepository: ReviewRepository) {
    fun getReviewsForDestination(username: String, destinationId: Long): ResponseEntity<String> {
        if (userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        if (destinationService.destinationWithIdExists(destinationId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Destination does not exist")
        }
        val reviews: List<Review> = findReviewsByDestination(destinationService.findDestinationById(destinationId)!!)

        val getReviews: List<ReviewResponse> = reviews.map { ReviewResponse(it) }

        return ResponseEntity.ok().body(getReviews.toString())
    }

    fun reviewDestination(username: String, destinationId: Long, reviewRequest: PostReview): ResponseEntity<String> {
        if (userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        if (destinationService.destinationWithIdExists(destinationId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Destination does not exist")
        }

        val review = Review(reviewRequest, destinationService.findDestinationById(destinationId)!!,
            userService.findUserByUsername(username)!!)

        reviewRepository.save(review)

        return ResponseEntity.status(HttpStatus.CREATED).body(ReviewResponse(review).toString())
    }

    fun editReview(username: String, reviewId: Long, reviewRequest: PostReview): ResponseEntity<String> {
        if (userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        val review: Review = findReviewById(reviewId)!!

        if (review.user.username != username) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Review does not belong to $username")
        }

        review.title = reviewRequest.title
        review.content = reviewRequest.content
        review.starRating = reviewRequest.starRating

        reviewRepository.save(review)

        return ResponseEntity.ok().body(ReviewResponse(review).toString())
    }

    fun deleteReview(username: String, reviewId: Long): ResponseEntity<String> {
        if (userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        if (reviewWithIdExists(reviewId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review does not exist")
        }

        val review: Review = findReviewById(reviewId)!!

        if (review.user.username != username) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Review does not belong to $username")
        }

        reviewRepository.delete(review)

        return ResponseEntity.ok().body("Deleted review with id: $reviewId")
    }

    fun findReviewById(reviewId: Long): Review? {
        return reviewRepository.findReviewById(reviewId)
    }

    fun reviewWithIdExists(id: Long): Boolean {
        return reviewRepository.findReviewById(id) == null
    }

    fun findReviewsByUser(user: User): List<Review> {
        return reviewRepository.findReviewsByUser(user)
    }

    fun findReviewsByDestination(destination: Destination): List<Review> {
        return reviewRepository.findReviewsByDestination(destination)
    }
}