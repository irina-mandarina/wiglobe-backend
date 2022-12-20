package com.example.demo.models.requestModels

import com.example.demo.entities.*
import com.example.demo.models.responseModels.Journey
import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime

class UserRequest(user: UserEntity) {
    var email: String? = ""
    var username: String? = ""
    var firstName: String? = ""
    var lastName: String? = ""
    var registrationDate: Timestamp? = Timestamp.valueOf(LocalDateTime.now())
    var biography: String? = ""
    var birthdate: Date? = Date.valueOf(LocalDate.now())
    var journeys: List<Journey> = listOf()
//
//    init {
//        this.email = ""
//        this.username = ""
//        this.firstName = ""
//        this.lastName = ""
//        this.registrationDate = Timestamp.valueOf(LocalDateTime.now())
//        this.biography = ""
//        this.birthdate = Date.valueOf(LocalDate.now())
//        this.journeys = user.journeys.map { journey ->
//            Journey(
//                    journey.id!!,
//                    UserNames(
//                        journey.user.username, journey.user.firstName, journey.user.lastName),
//                    journey.startDate,
//                    journey.endDate!!,
//                    journey.description,
//                    destination journey.destination.name,
//                    journey.activities.map { activity ->
//                        Activity(
//                            activity.id!!,
//                            activity.title,
//                            activity.description,
//                            activity.type,
//                            activity.date,
//                            activity.location
//                        )
//                    },
//                    journey.visibility
//            )
//        }
//    }
}