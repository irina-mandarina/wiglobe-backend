package com.example.demo.services

import com.example.demo.entities.DestinationEntity
import com.example.demo.entities.ReviewEntity
import com.example.demo.entities.UserEntity
import com.example.demo.repositories.ReviewRepository
import com.example.demo.models.responseModels.Review
import com.example.demo.models.requestModels.ReviewRequest
import com.example.demo.models.responseModels.Destination
import com.example.demo.models.responseModels.UserNames
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ReviewService(private val destinationService: DestinationService, private val userService: UserService,
                    private val reviewRepository: ReviewRepository) {
    fun getReviewsForDestination(username: String, destinationId: Long): ResponseEntity<List<Review>> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(
                "message", "Username does not exist")
                .body(null)
        }

        if (!destinationService.destinationWithIdExists(destinationId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message", "Destination does not exist")
                .body(null)
        }
        val reviews: List<ReviewEntity> = findReviewsByDestination(destinationService.findDestinationById(destinationId)!!)

        val getReviews: List<Review> = reviews.map {
            Review(
                it.id!!,
                Destination(
                    it.destination.latitude, it.destination.longitude, it.destination.name
                ),
                UserNames(
                    it.user.username, it.user.firstName, it.user.lastName
                ),
                it.starRating,
                it.reviewedDate,
                it.title!!,
                it.content!!
            )
        }

        return ResponseEntity.ok().body(
            getReviews
        )
    }

    fun reviewDestination(username: String, destinationId: Long, reviewRequest: ReviewRequest): ResponseEntity<Review> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(
                "message", "Username does not exist")
                .body(null)
        }

        if (!destinationService.destinationWithIdExists(destinationId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message", "Destination does not exist")
                .body(null)
        }

        val review = ReviewEntity(reviewRequest, destinationService.findDestinationById(destinationId)!!,
            userService.findUserByUsername(username)!!)

        reviewRepository.save(review)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            Review(
                review.id!!,
                Destination(
                    review.destination.latitude, review.destination.longitude, review.destination.name
                ),
                UserNames(
                    review.user.username, review.user.firstName, review.user.lastName
                ),
                review.starRating,
                review.reviewedDate,
                review.title!!,
                review.content!!
            )
        )
    }

    fun editReview(username: String, reviewId: Long, reviewRequest: ReviewRequest): ResponseEntity<Review> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(
                "message", "Username does not exist")
                .body(null)
        }

        if (!reviewWithIdExists(reviewId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message", "Review does not exist")
                .body(null)
        }

        val review: ReviewEntity = findReviewById(reviewId)!!

        if (review.user.username != username) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(
                "message", "Review does not belong to $username")
                .body(null)
        }

        review.title = reviewRequest.title
        review.content = reviewRequest.content
        review.starRating = reviewRequest.starRating

        reviewRepository.save(review)

        return ResponseEntity.ok().body(
            Review(
                review.id!!,
                Destination(
                    review.destination.latitude, review.destination.longitude, review.destination.name
                ),
                UserNames(
                    review.user.username, review.user.firstName, review.user.lastName
                ),
                review.starRating,
                review.reviewedDate,
                review.title!!,
                review.content!!
            )
        )
    }

    fun deleteReview(username: String, reviewId: Long): ResponseEntity<String> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(
                "message", "Username does not exist")
                .body(null)
        }

        if (!reviewWithIdExists(reviewId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message", "Review does not exist")
                .body(null)
        }

        val review: ReviewEntity = findReviewById(reviewId)!!

        if (review.user.username != username) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(
                "message", "Review does not belong to $username")
                .body(null)
        }

        reviewRepository.delete(review)

        return ResponseEntity.ok().header(
                "message", "Successfully deleted a review.")
            .body(null)
    }

    fun findReviewById(reviewId: Long): ReviewEntity? {
        return reviewRepository.findReviewById(reviewId)
    }

    fun reviewWithIdExists(id: Long): Boolean {
        return reviewRepository.findReviewById(id) != null
    }

    fun findReviewsByUser(user: UserEntity): List<ReviewEntity> {
        return reviewRepository.findReviewsByUser(user)
    }

    fun findReviewsByDestination(destination: DestinationEntity): List<ReviewEntity> {
        return reviewRepository.findReviewsByDestination(destination)
    }
}