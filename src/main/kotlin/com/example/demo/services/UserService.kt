package com.example.demo.services

import com.example.demo.entities.User
import com.example.demo.requestEntities.UserRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

interface UserService {
    fun logIn(@RequestBody userRequest: UserRequest): ResponseEntity<String>
    fun register(@RequestBody userRequest: UserRequest): ResponseEntity<String>
    fun logOut(@RequestHeader username: String): ResponseEntity<String>
    fun deleteAccount(@RequestHeader username: String): ResponseEntity<String>
    fun setBio(@RequestHeader username: String, @RequestBody bio: String): ResponseEntity<String>
    fun userWithUsernameExists(username: String): Boolean
    fun userWithEmailExists(email: String): Boolean
    fun findUserById(id: Long): User?
    fun findUserByUsername(username: String): User?
    fun getUserDetails(username: String): ResponseEntity<String>
}