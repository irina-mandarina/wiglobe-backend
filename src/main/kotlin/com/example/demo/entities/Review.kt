package com.example.demo.entities

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "reviews")
class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    var user: User = User()

    @JoinColumn(name = "journey_id", nullable = false)
    @ManyToOne
    var journey: Journey = Journey()

    @Column(name = "created_date", nullable = false)
    var createdDate: Timestamp = TODO()

    @Column(name = "star_rating", nullable = false)
//    @Min(value = 1)
//    @Max(value = 10)
    var starRating: Int = TODO()

    @Column(name = "title", nullable = false)
    var title: String = ""

    @Column(name = "content", nullable = false)
    var content: String = ""

}