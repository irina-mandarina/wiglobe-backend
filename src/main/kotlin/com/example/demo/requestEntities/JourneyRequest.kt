package com.example.demo.requestEntities

import com.example.demo.entities.Activity
import com.example.demo.entities.Journey
import com.example.demo.entities.User
import java.sql.Timestamp

class JourneyRequest {
    var id: Long? = null
    var user: User = User()
    var startDate: Timestamp = TODO()
    var endDate: Timestamp = TODO()
    val description: String?
    var destination: String = ""
    var activities: List<Activity>

    constructor(journey: Journey) {
        this.id = journey.id
        this.description = journey.description
        user = journey.user
        destination = journey.destination.toString()
        activities = journey.activities
        startDate = journey.startDate!!
        endDate = journey.endDate!!
    }
}