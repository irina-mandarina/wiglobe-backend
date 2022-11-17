package com.example.demo.requestEntities

import com.example.demo.entities.Review
import java.sql.Timestamp

class ReviewRequest {
    var id: Long? = null
    var userId: Long? = null
    var destinationId: Long? = null
    var reviewedDate: Timestamp? = null
    var starRating: Int? = null
    var title: String = ""
    var content: String = ""

    constructor(review: Review) {
        this.id = review.id
        this.userId = review.user.id
        this.destinationId = review.destination.id
        this.reviewedDate = review.reviewedDate
        this.starRating = review.starRating
        this.title = review.title!!
        this.content = review.content!!
    }
}