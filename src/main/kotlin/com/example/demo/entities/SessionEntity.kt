package com.example.demo.entities

import org.springframework.beans.factory.annotation.Value
import java.sql.Timestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "sessions")
class SessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = -1

    @JoinColumn(name = "user_id")
    @ManyToOne
    lateinit var user: UserEntity

    @Column(name = "session_start")
    var sessionStart: Timestamp = Timestamp.valueOf(LocalDateTime.now())

//    @Value("${session.duration}")
//    var sessionDuration: Int = 0
    public fun isValid(): Boolean {
        return sessionStart.toLocalDateTime().plusMinutes(5).isAfter(LocalDateTime.now())
    }
}