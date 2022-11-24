package com.example.demo.entities

import com.example.demo.requestEntities.PostComment
import java.sql.Timestamp
import java.time.LocalDateTime
import javax.persistence.*
import org.springframework.data.relational.core.mapping.Table

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

    constructor(commentRequest: PostComment, journey: Journey,
                user: User) : this() {
        this.user = user
        this.journey = journey
        this.datePosted = Timestamp.valueOf(LocalDateTime.now())
        this.content = commentRequest.content
    }

}