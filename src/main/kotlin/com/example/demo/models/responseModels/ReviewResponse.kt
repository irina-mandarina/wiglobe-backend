package com.example.demo.models.responseModels

import com.example.demo.entities.Review
import kotlinx.serialization.Serializable


@Serializable
data class ReviewResponse(val review: Review) {
    val id = review.id
    val destinationId = review.destination.id
    val userNames = UserNamesResponse(review.user.username,
        review.user.firstName, review.user.lastName)
    var starRating: Int = review.starRating
    val title = review.title
    val reviewedDate = review.reviewedDate.toString()
    val content = review.content
}
