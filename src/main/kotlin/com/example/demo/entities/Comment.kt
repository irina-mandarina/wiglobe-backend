package com.example.demo.entities

import com.example.demo.requestEntities.CommentRequest
import java.sql.Timestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "comments")
class Comment() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @JoinColumn(name = "journey_id", nullable = false)
    @ManyToOne
    var journey: Journey = Journey()

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    lateinit var user: User

    @Column(name = "date_posted", nullable = false)
    var datePosted: Timestamp = Timestamp.valueOf(LocalDateTime.now())

    @Column(name = "content", nullable = false)
    var content: String = ""

    constructor(commentRequest: CommentRequest, journey: Journey, user: User) : this() {
        this.user = user
        this.journey = journey
        this.datePosted = commentRequest.datePosted
        this.content = commentRequest.content
    }

}