package com.example.demo.requestEntities

import com.example.demo.entities.Activity
import com.example.demo.entities.Journey
import com.example.demo.entities.User
import com.example.demo.services.JourneyService
import java.sql.Timestamp

class GetJourney {
    var id: Long? = null

    var user: User = User()

    var startDate: Timestamp = TODO()

    var endDate: Timestamp = TODO()

    var destination: String = ""

    var activities: List<Activity>

    constructor(journey: Journey) {
        user = journey.user
        destination = journey.destination.toString()
        activities = journey.activities
        startDate = journey.startDate!!
        endDate = journey.endDate!!
    }
}