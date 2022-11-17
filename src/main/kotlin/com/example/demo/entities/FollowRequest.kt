package com.example.demo.entities

import lombok.Getter
import lombok.NoArgsConstructor
import lombok.RequiredArgsConstructor
import lombok.Setter
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import javax.persistence.*

@Entity
@Table(name = "follow_requests")
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
class FollowRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Column(name = "request_date", nullable = false)
    var requestDate: Timestamp = Timestamp.valueOf(LocalDateTime.now())

    @JoinColumn(name = "requester_id", nullable = false)
    @ManyToOne
    lateinit var requester: User

    @JoinColumn(name = "receiver_id", nullable = false)
    @ManyToOne
    lateinit var receiver: User

}