package com.example.demo.entities

import com.example.demo.models.requestModels.PostActivity
import com.example.demo.types.ActivityTypes
import java.sql.Timestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "activities")
class Activity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @JoinColumn(name = "journey_id", nullable = false)
    @ManyToOne
    lateinit var journey: Journey

    @Column(name = "date", nullable = false)
    var date: Timestamp = Timestamp.valueOf(LocalDateTime.now())

    @Column(name = "title", nullable = false)
    var title: String = ""

    @Column(name = "type", nullable = false)
    var type: ActivityTypes = ActivityTypes.OTHER

    @Column(name = "description")
    var description: String = ""

    @Column(name = "location", nullable = false)
    var location: String = ""

    constructor(postActivity: PostActivity, journeyId: Long) : this() {
        this.title = postActivity.title
        this.location = postActivity.location
        this.description = postActivity.description
        this.type = postActivity.type
    }
}