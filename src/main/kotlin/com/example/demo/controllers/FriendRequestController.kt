package com.example.demo.controllers

import com.example.demo.services.FriendRequestService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class FriendRequestController {
    private val friendRequestService: FriendRequestService = TODO()

    @PostMapping("/users/{userId}/add-friend")
    fun sendFriendRequest(@RequestHeader username: String, @PathVariable userId: Long): ResponseEntity<String> {
        return friendRequestService.sendFriendRequest(username, userId)
    }

    @DeleteMapping("/users/{userId}/add-friend")
    fun deleteFriendRequest(@RequestHeader username: String, @PathVariable userId: Long): ResponseEntity<String> {
        return friendRequestService.deleteFriendRequest(username, userId)
    }

    @GetMapping("/me/friendRequests")
    fun getFriendRequests(@RequestHeader username: String) : ResponseEntity<String> {
        return friendRequestService.getFriendRequests(username)
    }

    @DeleteMapping("/me/friendRequests/{friendRequestId}")
    fun respondToFriendRequest(@RequestHeader username: String, @PathVariable friendRequestId: Long, @RequestBody response: Boolean): ResponseEntity<String> {
        return friendRequestService.respondToFriendRequest(username, friendRequestId, response)
    }
}