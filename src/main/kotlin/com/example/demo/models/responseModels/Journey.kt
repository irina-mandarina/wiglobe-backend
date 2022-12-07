package com.example.demo.models.responseModels

import com.example.demo.serialization.TimestampSerializer
import com.example.demo.types.Visibility
import kotlinx.serialization.Serializable
import java.sql.Timestamp

@Serializable
data class Journey(val id: Long,
                   val usernames: UserNames,
                   @Serializable(TimestampSerializer::class) val startDate: Timestamp,
                   @Serializable(TimestampSerializer::class) val endDate: Timestamp,
                   val description: String,
                   val destination: String,
                   val activities: List<Activity>,
                   val visibility: Visibility
                           )