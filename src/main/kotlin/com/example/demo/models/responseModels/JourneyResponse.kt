package com.example.demo.models.responseModels

import com.example.demo.serialization.TimestampSerializer
import kotlinx.serialization.Serializable
import java.sql.Timestamp

@Serializable
data class JourneyResponse(val id: Long,
                           val usernames: UserNamesResponse,
                           @Serializable(TimestampSerializer::class) val startDate: Timestamp,
                           @Serializable(TimestampSerializer::class) val endDate: Timestamp,
                           val description: String,
                           val destination: String
                           )