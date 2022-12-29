package com.example.demo.controllers

import com.example.demo.models.requestModels.LogInRequest
import com.example.demo.models.requestModels.SignUpRequest
import com.example.demo.models.responseModels.UserDetails
import com.example.demo.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
class UserController(private val userService: UserService) {
    @PostMapping("/users/login")
    fun logIn(@RequestBody logInRequest: LogInRequest): ResponseEntity<UserDetails> {
        return userService.logIn(logInRequest)
    }

    @PostMapping("/users/signup")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<UserDetails> {
        return userService.signUp(signUpRequest)
    }

    @PostMapping("/users/logout")
    fun logOut(@RequestHeader username: String): ResponseEntity<String> {
        return userService.logOut(username)
    }

    @GetMapping("/users/{username}")
    fun getUserDetails(@RequestHeader username: String,
                       @PathVariable("username") otherUserUsername: String): ResponseEntity<UserDetails> {
        return userService.getUserDetails(username, otherUserUsername)
    }


//    @GetMapping("/users/details") // ??
//    fun getMyDetails(@RequestHeader username: String): ResponseEntity<String> {
//        return userService.getUserDetails(username)
//    }

    @DeleteMapping("/users/{username}")
    fun deleteAccount(@RequestHeader username: String): ResponseEntity<String> {
        return userService.deleteAccount(username)
    }

    @PutMapping("/users/{username}/bio")
    fun editBio(@RequestHeader username: String, @RequestBody bio: String): ResponseEntity<String> {
        return userService.setBio(username, bio)
    }
}