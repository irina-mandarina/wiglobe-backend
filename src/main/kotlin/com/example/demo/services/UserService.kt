package com.example.demo.services

import com.example.demo.entities.User
import com.example.demo.repositories.UserRepository
import com.example.demo.requestEntities.LogInRequest
import com.example.demo.requestEntities.SignUpRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

interface UserService() {
    fun logIn(@RequestBody logInRequest: LogInRequest): ResponseEntity<String>
    fun signUp(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<String>
    fun logOut(@RequestHeader username: String): ResponseEntity<String>
    fun deleteAccount(@RequestHeader username: String): ResponseEntity<String>
    fun setBio(@RequestHeader username: String, @RequestBody bio: String): ResponseEntity<String>
    fun userWithUsernameExists(username: String): Boolean
    fun userWithEmailExists(email: String): Boolean
    fun findUserById(id: Long): User?
    fun findUserByUsername(username: String): User?
    fun getUserDetails(username: String): ResponseEntity<String>
}