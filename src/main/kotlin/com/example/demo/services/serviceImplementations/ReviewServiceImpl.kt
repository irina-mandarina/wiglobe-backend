package com.example.demo.services.serviceImplementations

import com.example.demo.entities.Destination
import com.example.demo.entities.Review
import com.example.demo.entities.User
import com.example.demo.repositories.ReviewRepository
import com.example.demo.requestEntities.ReviewRequest
import com.example.demo.services.DestinationService
import com.example.demo.services.ReviewService
import com.example.demo.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class ReviewServiceImpl(val destinationService: DestinationService, val userService: UserService,
                        val reviewRepository: ReviewRepository): ReviewService {
    override fun getReviewsForDestination(username: String, destinationId: Long): ResponseEntity<String> {
        if (userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        if (destinationService.destinationWithIdExists(destinationId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("destination does not exist")
        }
        val reviews: List<Review> = findReviewsByDestination(destinationService.findDestinationById(destinationId)!!)

        val getReviews: List<ReviewRequest> = reviews.map { ReviewRequest(it) }

        return ResponseEntity.ok().body(getReviews.toString())
    }

    override fun reviewDestination(username: String, reviewRequest: ReviewRequest): ResponseEntity<String> {
        if (userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        if (destinationService.destinationWithIdExists(reviewRequest.destinationId!!)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("destination does not exist")
        }

        val review = Review()
        review.destination = destinationService.findDestinationById(reviewRequest.destinationId!!)!!
        review.user = userService.findUserByUsername(username)!!
        review.content = reviewRequest.content
        review.reviewedDate = Timestamp.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss")))
        review.title = reviewRequest.title
        review.starRating = reviewRequest.starRating!!

        reviewRepository.save(review)

        return ResponseEntity.status(HttpStatus.CREATED).body(ReviewRequest(review).toString())
    }

    override fun editReview(username: String, reviewRequest: ReviewRequest): ResponseEntity<String> {
        if (userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        if (destinationService.destinationWithIdExists(reviewRequest.destinationId!!)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Destination does not exist")
        }

        val review: Review = findReviewById(reviewRequest.id!!)!!

        if (review.user.username != username) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Review does not belong to $username")
        }

        review.title = reviewRequest.title
        review.content = reviewRequest.content
        review.starRating = reviewRequest.starRating!!

        reviewRepository.save(review)

        return ResponseEntity.ok().body(ReviewRequest(review).toString())
    }

    override fun deleteReview(username: String, reviewId: Long): ResponseEntity<String> {
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

    override fun findReviewById(reviewId: Long): Review? {
        return reviewRepository.findReviewById(reviewId)
    }

    override fun reviewWithIdExists(id: Long): Boolean {
        return reviewRepository.findReviewById(id) == null
    }

    override fun findReviewsByUser(user: User): List<Review> {
        return reviewRepository.findReviewsByUser(user)
    }

    override fun findReviewsByDestination(destination: Destination): List<Review> {
        return reviewRepository.findReviewsByDestination(destination)
    }
}