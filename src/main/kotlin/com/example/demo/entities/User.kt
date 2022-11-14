package com.example.demo.entities

import java.sql.Timestamp
import javax.persistence.*

@Entity
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Column(name = "email", nullable = false, unique = true)
    var email: String = ""

    @Column(name = "username", nullable = false, unique = true)
    var username: String = ""

    @Column(name = "first_name", nullable = false)
    var firstName: String = ""

    @Column(name = "lastName", nullable = false)
    var lastName: String = ""

    @Column(name = "registration_date", nullable = false)
    var registrationDate: Timestamp = TODO()

    @Column(name = "biography")
    var biography: String? = ""

    @Column(name = "birthdate", nullable = false)
    var birthdate: Timestamp = TODO()

    @OneToMany(mappedBy="user")
    var journeys: List<Journey>

    @OneToMany(mappedBy="user")
    var reviews: List<Review>

    @OneToMany(mappedBy="user")
    var comments: List<Comment>

    @OneToMany(mappedBy="friend1")
    var friendships: List<Friendship>

    @OneToMany(mappedBy="receiver")
    var receivedFriendRequests: List<FriendRequest>

    @OneToMany(mappedBy="requester")
    var sentFriendRequests: List<FriendRequest>
}