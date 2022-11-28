package com.example.demo.models.responseModels

import com.example.demo.serialization.DateSerializer
import com.example.demo.serialization.TimestampSerializer
import kotlinx.serialization.Serializable
import java.sql.Date
import java.sql.Timestamp

@Serializable
data class UserDetailsResponse(val username: String,
                               val firstName: String,
                               val lastName: String,
                               @Serializable(DateSerializer::class)
                               val birthdate: Date,
                               val biography: String,
                               @Serializable(TimestampSerializer::class)
                               val registrationDate: Timestamp
                               )
