package com.example.demo.services

import com.example.demo.requestEntities.LogInRequest
import com.example.demo.requestEntities.RegistrationRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

interface UserService {
    fun logIn(@RequestBody logInRequest: LogInRequest): ResponseEntity<String>

    fun register(@RequestBody registrationRequest: RegistrationRequest): ResponseEntity<String>

    fun logOut(@RequestHeader username: String): ResponseEntity<String>

    fun deleteAccount(@RequestHeader username: String): ResponseEntity<String>

    fun addBio(@RequestHeader username: String, @RequestBody bio: String): ResponseEntity<String>

    fun editBio(@RequestHeader username: String, @RequestBody bio: String): ResponseEntity<String>
}