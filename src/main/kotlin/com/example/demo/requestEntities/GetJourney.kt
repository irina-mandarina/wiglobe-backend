package com.example.demo.requestEntities

import com.example.demo.entities.*

data class GetJourney(val journey: Journey) {
    val id = journey.id
    var username = journey.user.username
    var startDate = journey.startDate
    var endDate = journey.endDate
    var destination = GetDestination(journey.destination)
    var description = journey.description
}