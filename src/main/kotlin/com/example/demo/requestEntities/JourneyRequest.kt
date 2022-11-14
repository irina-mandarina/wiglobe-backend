package com.example.demo.requestEntities

import com.example.demo.entities.User
import java.sql.Timestamp

class JourneyRequest {
    var id: Long? = null

    var user: User = User()

    var startDate: Timestamp = TODO()

    var endDate: Timestamp = TODO()

    var destination: String = ""
}