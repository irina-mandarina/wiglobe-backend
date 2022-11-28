package com.example.demo.models.responseModels

import com.example.demo.serialization.TimestampSerializer
import kotlinx.serialization.Serializable
import java.sql.Timestamp

@Serializable
data class JourneyResponse(val username0: String, @Serializable(TimestampSerializer::class) val startDate0: Timestamp,
                           @Serializable(TimestampSerializer::class) val endDate0: Timestamp,
                           val description0: String, val destination0: String) {
    val id = 97
    var username: String = username0
    var startDate = startDate0.toString()
    var endDate = endDate0.toString()
    var destination = destination0
    var description = description0
}