package com.example.demo.requestEntities

import com.example.demo.entities.Review


data class GetReview(val review: Review) {
    val id = review.id
    val destinationId = review.destination.id
    val userNames = GetUserNames(review.user.username,
        review.user.firstName, review.user.lastName)
    var starRating: Int = review.starRating
    val title = review.title
    val reviewedDate = review.reviewedDate
    val content = review.content
}
