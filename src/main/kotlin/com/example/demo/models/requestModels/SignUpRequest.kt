package com.example.demo.models.requestModels

import com.example.demo.types.Gender
import java.sql.Date

data class SignUpRequest(val username: String,
                         val email: String,
                         val password: String,
                         val firstName: String,
                         val lastName: String,
                         val biography: String,
                         val birthdate: Date?,
                         val gender: Gender?)