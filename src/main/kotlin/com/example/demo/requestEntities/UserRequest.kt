package com.example.demo.requestEntities

import com.example.demo.entities.*
import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime

class UserRequest {
    var id: Long? = null
    var email: String = ""
    var username: String = ""
    var password: String = ""
    var firstName: String = ""
    var lastName: String = ""
    var registrationDate: Timestamp = Timestamp.valueOf(LocalDateTime.now())
    var biography: String? = ""
    var birthdate: Date = Date.valueOf(LocalDate.now())
    var journeys: List<JourneyRequest> = listOf()

    constructor(user: User) {
        this.id = null
        this.email = ""
        this.username = ""
        this.password = ""
        this.firstName = ""
        this.lastName = ""
        this.registrationDate = Timestamp.valueOf(LocalDateTime.now())
        this.biography = ""
        this.birthdate = Date.valueOf(LocalDate.now())
        this.journeys = user.journeys.map { JourneyRequest(it) }
    }
}