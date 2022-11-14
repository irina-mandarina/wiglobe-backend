package com.example.demo.entities

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "friendships")
class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Column(name = "created_date", nullable = false)
    var createdDate: Timestamp = TODO()

    @JoinColumn(name = "friend1_id", nullable = false)
    @ManyToOne
    var friend1: User = User()

    @JoinColumn(name = "friend2_id", nullable = false)
    @ManyToOne
    var friend2: User = User()
}