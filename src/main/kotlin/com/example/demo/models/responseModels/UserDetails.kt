package com.example.demo.models.responseModels

import com.example.demo.types.Gender
import com.example.demo.types.ProfilePrivacy
import java.sql.Date
import java.sql.Timestamp

data class UserDetails(
    val username: String,
    val firstName: String,
    val lastName: String?,
    val birthdate: Date?,
    val biography: String?,
    val registrationTimestamp: Timestamp?,
    val gender: Gender,
    val residence: Destination?,
    val profilePrivacy: ProfilePrivacy,
    val profilePicture: String?,
    val backgroundPicture: String?
)
