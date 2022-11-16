package com.example.demo.requestEntities

import com.example.demo.entities.Activity
import com.example.demo.entities.User
import java.sql.Timestamp

class JourneyRequest {
    var id: Long? = 0

    var user: User = User()

    var startDate: Timestamp = TODO()

    var endDate: Timestamp = TODO()

    var destination: String = ""

    val description: String

    var activities: List<Activity>
}