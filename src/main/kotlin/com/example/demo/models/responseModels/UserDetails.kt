package com.example.demo.models.responseModels

import com.example.demo.serialization.DateSerializer
import com.example.demo.serialization.TimestampSerializer
import com.example.demo.types.Gender
import com.example.demo.types.ProfilePrivacy
import kotlinx.serialization.Serializable
import java.sql.Date
import java.sql.Timestamp

@Serializable
data class UserDetails(
    val username: String,
    val firstName: String,
    val lastName: String?,
    @Serializable(DateSerializer::class)
    val birthdate: Date?,
    val biography: String?,
    @Serializable(TimestampSerializer::class)
    val registrationDate: Timestamp?,
    val gender: Gender,
    val residence: Destination?,
    val privacy: ProfilePrivacy
)
