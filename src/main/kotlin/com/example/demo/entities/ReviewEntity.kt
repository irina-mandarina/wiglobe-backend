package com.example.demo.entities

import com.example.demo.models.requestModels.ReviewRequest
import java.sql.Timestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "reviews")
class ReviewEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    lateinit var user: UserEntity

    @JoinColumn(name = "destination_id", nullable = false)
    @ManyToOne
    lateinit var destination: DestinationEntity

    @Column(name = "reviewed_date", nullable = false)
    var reviewedDate: Timestamp = Timestamp.valueOf(LocalDateTime.now())

    @Column(name = "star_rating", nullable = false)
//    @Min(value = 1)
//    @Max(value = 10)
    var starRating: Int = 1

    @Column(name = "title")
    var title: String? = ""

    @Column(name = "content")
    var content: String? = ""

    constructor(reviewRequest: ReviewRequest, reviewedDestination: DestinationEntity, user: UserEntity) : this() {
        this.user = user
        this.reviewedDate = Timestamp.valueOf(LocalDateTime.now())
        this.starRating = reviewRequest.starRating
        this.destination = reviewedDestination
        this.title = reviewRequest.title
        this.content = reviewRequest.content
    }

}