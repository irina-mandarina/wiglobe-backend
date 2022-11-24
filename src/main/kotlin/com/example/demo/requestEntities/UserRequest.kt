package com.example.demo.requestEntities

import com.example.demo.entities.*
import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime

class UserRequest(user: User) {
    var email: String? = ""
    var username: String? = ""
    var firstName: String? = ""
    var lastName: String? = ""
    var registrationDate: Timestamp? = Timestamp.valueOf(LocalDateTime.now())
    var biography: String? = ""
    var birthdate: Date? = Date.valueOf(LocalDate.now())
    var journeys: List<GetJourney> = listOf()

    init {
        this.email = ""
        this.username = ""
        this.firstName = ""
        this.lastName = ""
        this.registrationDate = Timestamp.valueOf(LocalDateTime.now())
        this.biography = ""
        this.birthdate = Date.valueOf(LocalDate.now())
        this.journeys = user.journeys.map { GetJourney(it) }
    }
}