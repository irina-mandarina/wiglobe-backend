package com.example.demo.entities

import com.example.demo.models.requestModels.CommentRequest
import java.sql.Timestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "comments")
class CommentEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = -1

    @JoinColumn(name = "journey_id", nullable = false)
    @ManyToOne
    var journey: JourneyEntity = JourneyEntity()

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    lateinit var user: UserEntity

    @Column(name = "posted_on", nullable = false)
    var postedOn: Timestamp = Timestamp.valueOf(LocalDateTime.now())

    @Column(name = "content", nullable = false)
    var content: String = ""

    constructor(commentRequest: CommentRequest, journey: JourneyEntity,
                user: UserEntity) : this() {
        this.user = user
        this.journey = journey
        this.postedOn = Timestamp.valueOf(LocalDateTime.now())
        this.content = commentRequest.content
    }

}