package com.example.demo.controllers

import com.example.demo.models.requestModels.GooglePayload
import com.example.demo.models.requestModels.LogInRequest
import com.example.demo.models.requestModels.SignUpRequest
import com.example.demo.models.responseModels.LogInResponse
import com.example.demo.models.responseModels.SignUpResponse
import com.example.demo.models.responseModels.UserDetails
import com.example.demo.services.UserService
import com.example.demo.types.Gender
import com.example.demo.types.ProfilePrivacy
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.sql.Date

@RestController
@CrossOrigin("http://localhost:3000")
class UserController(private val userService: UserService) {
    @PostMapping("/users/login")
    fun logIn(@RequestBody logInRequest: LogInRequest): ResponseEntity<LogInResponse> {
        return userService.logIn(logInRequest)
    }

    @PostMapping("/users/signup")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<SignUpResponse> { // taken user details
        return userService.signUp(signUpRequest)
    }

    @PostMapping("/users/login/google")
    fun authenticateWithGoogle(@RequestAttribute token: String, @RequestBody googlePayload: GooglePayload): ResponseEntity<LogInResponse> {
        return userService.authenticateWithGoogle(token, googlePayload)
    }

    @PostMapping("/users/logout")
    fun logOut(@RequestAttribute username: String): ResponseEntity<String> {
        return userService.logOut(username)
    }

    @GetMapping("/users/{username}")
    fun getUserDetails(@RequestAttribute username: String,
                       @PathVariable("username") otherUserUsername: String): ResponseEntity<UserDetails> {
        return userService.getUserDetails(username, otherUserUsername)
    }

    @DeleteMapping("/users/{username}")
    fun deleteAccount(@RequestAttribute username: String): ResponseEntity<String> {
        return userService.deleteAccount(username)
    }

    @PutMapping("/users/{username}/bio")
    fun editBio(@RequestAttribute username: String, @RequestBody bio: String): ResponseEntity<UserDetails> {
        return userService.setBio(username, bio)
    }

    @PutMapping("/users/{username}/residence")
    fun editResidence(@RequestAttribute username: String, @RequestBody destinationId: Long): ResponseEntity<UserDetails> {
        return userService.setResidence(username, destinationId)
    }

    @PutMapping("/users/{username}/privacy")
    fun editProfilePrivacy(@RequestAttribute username: String, @RequestBody privacy: ProfilePrivacy): ResponseEntity<UserDetails> {
        return userService.setProfilePrivacy(username, privacy)
    }

    @PutMapping("/users/{username}/gender")
    fun editGender(@RequestAttribute username: String, @RequestBody gender: Gender): ResponseEntity<UserDetails> {
        return userService.setGender(username, gender)
    }

    @PutMapping("/users/{username}/birthdate")
    fun editBirthdate(@RequestAttribute username: String, @RequestBody birthdate: Date): ResponseEntity<UserDetails> {
        return userService.setBirthdate(username, birthdate)
    }
}