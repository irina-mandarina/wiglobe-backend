package com.example.demo.entities

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "comments")
class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Column(name = "user_id", nullable = false)
    @ManyToOne
    var user: User = User()

    @Column(name = "date_posted", nullable = false)
    var datePosted: Timestamp = TODO()

    @Column(name = "content", nullable = false)
    var content: Timestamp = TODO()

}