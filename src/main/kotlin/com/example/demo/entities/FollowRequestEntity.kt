package com.example.demo.entities

import java.sql.Timestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "follow_requests")
class FollowRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = -1

    @Column(name = "request_date", nullable = false)
    var requestDate: Timestamp = Timestamp.valueOf(LocalDateTime.now())

    @JoinColumn(name = "requester_id", nullable = false)
    @ManyToOne(cascade = [CascadeType.REMOVE])
    lateinit var requester: UserEntity

    @JoinColumn(name = "receiver_id", nullable = false)
    @ManyToOne
    lateinit var receiver: UserEntity

}