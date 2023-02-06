package com.example.demo.entities

import com.example.demo.types.NotificationObjectEntityType
import java.sql.Timestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "notifications")
class NotificationEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @JoinColumn(name = "receiver_id", nullable = false)
    @ManyToOne
    lateinit var receiver: UserEntity

    @JoinColumn(name = "subject_id", nullable = false)
    @ManyToOne
    lateinit var subject: UserEntity

    @Column(name = "timestamp", nullable = false)
    var timestamp: Timestamp = Timestamp.valueOf(LocalDateTime.now())

    @Column(name = "object_type", nullable = true)
    @Enumerated(EnumType.STRING)
    var objectType: NotificationObjectEntityType? = null

    @Column(name = "object_id", nullable = true)
    var objectId: Long? = null

    @Column(name = "preposition_object_type", nullable = true)
    @Enumerated(EnumType.STRING)
    var prepositionObjectType: NotificationObjectEntityType? = null

    @Column(name = "preposition_object_id", nullable = true)
    var prepositionObjectId: Long? = null

    @Column(name = "content")
    var content: String = ""

}