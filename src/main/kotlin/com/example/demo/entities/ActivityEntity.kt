package com.example.demo.entities

import com.example.demo.models.requestModels.ActivityRequest
import com.example.demo.types.ActivityType
import java.sql.Timestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "activities")
class ActivityEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @JoinColumn(name = "journey_id", nullable = false)
    @ManyToOne
    lateinit var journey: JourneyEntity

    @Column(name = "date", nullable = false)
    var date: Timestamp? = null

    @Column(name = "title")
    var title: String? = ""

    @Column(name = "type")
    var type: ActivityType? = null

    @Column(name = "description")
    var description: String? = ""

    @Column(name = "location")
    var location: String? = ""

    constructor(activityRequest: ActivityRequest, journey: JourneyEntity) : this() {
        this.date = activityRequest.date
        this.journey = journey
        this.title = activityRequest.title
        this.location = activityRequest.location
        this.description = activityRequest.description
        if (activityRequest.type == null) {
           this.type = ActivityType.OTHER
        }
        else {
            this.type = activityRequest.type
        }
    }
}