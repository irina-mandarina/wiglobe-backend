package com.example.demo.controllers

import com.example.demo.requestEntities.UserRequest
import com.example.demo.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(val userService: UserService) {
    @PostMapping("/auth/login")
    fun logIn(@RequestBody userRequest: UserRequest): ResponseEntity<String> {
        return userService.logIn(userRequest)
    }

    @PostMapping("/auth/register")
    fun register(@RequestBody userRequest: UserRequest): ResponseEntity<String> {
        return userService.register(userRequest)
    }

    @PostMapping("/auth/logOut")
    fun logOut(@RequestHeader username: String): ResponseEntity<String> {
        return userService.logOut(username)
    }

    @GetMapping("/me/details")
    fun getUserDetails(@RequestHeader username: String): ResponseEntity<String> {
        return userService.getUserDetails(username)
    }

    @DeleteMapping("/me/delete-account")
    fun deleteAccount(@RequestHeader username: String): ResponseEntity<String> {
        return userService.deleteAccount(username)
    }

    @PostMapping("/me/bio")
    fun addBio(@RequestHeader username: String, @RequestBody bio: String): ResponseEntity<String> {
        return userService.setBio(username, bio)
    }

    @PutMapping("/me/bio")
    fun editBio(@RequestHeader username: String, @RequestBody bio: String): ResponseEntity<String> {
        return userService.setBio(username, bio)
    }
}