package com.example.demo.requestEntities

import java.sql.Date

data class SignUpRequest(val username: String, val email: String, val password: String,
val firstName: String, val lastName: String, val birthdate: Date)