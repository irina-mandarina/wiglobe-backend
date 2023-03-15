package com.example.demo.entities

import com.example.demo.models.requestModels.ActivityRequest
import com.example.demo.types.ActivityType
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "activities")
class ActivityEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = 1

    @JoinColumn(name = "journey_id", nullable = false)
    @ManyToOne
    lateinit var journey: JourneyEntity

    @Column(name = "date", nullable = false)
    var date: Timestamp? = null

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    var type: ActivityType? = null

    @Column(name = "description")
    var description: String? = ""

    @Column(name = "image")
    var image: String? = ""

    constructor(activityRequest: ActivityRequest, journey: JourneyEntity) : this() {
        this.date = activityRequest.date
        this.journey = journey
        this.image = activityRequest.image
        this.description = activityRequest.description
        if (activityRequest.type == null) {
           this.type = ActivityType.OTHER
        }
        else {
            this.type = activityRequest.type
        }
    }
}