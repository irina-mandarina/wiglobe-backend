package com.example.demo.controllers

import com.example.demo.services.FriendshipService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

class FriendshipController {
    private val friendshipService: FriendshipService = TODO()

    @GetMapping("/users/{userId}/friendship")
    fun getFriendshipStatus(@RequestHeader username: String, @PathVariable userId: Long): ResponseEntity<String> {
        return friendshipService.getFriendshipStatus(username, userId)
    }

    @DeleteMapping("/users/{userId}/friendship")
    fun endFriendship(@RequestHeader username: String, @PathVariable userId: Long): ResponseEntity<String> {
        return friendshipService.endFriendship(username, userId)
    }
}