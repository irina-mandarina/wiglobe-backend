package com.example.demo.controllers

import com.example.demo.requestEntities.LogInRequest
import com.example.demo.requestEntities.RegistrationRequest
import com.example.demo.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {
    private val userService: UserService = TODO()

    @PostMapping("/auth/login")
    fun logIn(@RequestBody logInRequest: LogInRequest): ResponseEntity<String> {
        return userService.logIn(logInRequest)
    }

    @PostMapping("/auth/register")
    fun register(@RequestBody registrationRequest: RegistrationRequest): ResponseEntity<String> {
        return userService.register(registrationRequest)
    }

    @PostMapping("/auth/logOut")
    fun logOut(@RequestHeader username: String): ResponseEntity<String> {
        return userService.logOut(username)
    }

    @DeleteMapping("/me/delete")
    fun deleteAccount(@RequestHeader username: String): ResponseEntity<String> {
        return userService.deleteAccount(username)
    }

    @PostMapping("/me/bio")
    fun addBio(@RequestHeader username: String, @RequestBody bio: String): ResponseEntity<String> {
        return userService.addBio(username, bio)
    }

    @PutMapping("/me/bio")
    fun editBio(@RequestHeader username: String, @RequestBody bio: String): ResponseEntity<String> {
        return userService.editBio(username, bio)
    }
}