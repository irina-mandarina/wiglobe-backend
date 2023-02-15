package com.example.demo.entities

import java.sql.Timestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "follow")
class FollowEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = -1

    @Column(name = "follow_date", nullable = false)
    var followDate: Timestamp = Timestamp.valueOf(LocalDateTime.now())

    @JoinColumn(name = "follower_id", nullable = false)
    @ManyToOne
    var follower: UserEntity = UserEntity()

    @JoinColumn(name = "followed_id", nullable = false)
    @ManyToOne
    var followed: UserEntity = UserEntity()

    constructor(followRequest: FollowRequestEntity) : this() {
        this.followDate = Timestamp.valueOf(LocalDateTime.now())
        this.followed = followRequest.receiver
        this.follower = followRequest.requester
    }

    constructor(follower: UserEntity, followed: UserEntity) : this() {
        this.followDate = Timestamp.valueOf(LocalDateTime.now())
        this.followed = followed
        this.follower = follower
    }
}