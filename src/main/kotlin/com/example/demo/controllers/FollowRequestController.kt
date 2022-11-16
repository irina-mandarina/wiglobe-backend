package com.example.demo.controllers

import com.example.demo.services.FollowRequestService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class FollowRequestController(val followRequestService: FollowRequestService) {
    @PostMapping("/users/{userId}/add-friend")
    fun sendFriendRequest(@RequestHeader username: String, @PathVariable userId: Long): ResponseEntity<String> {
        return followRequestService.sendFollowRequest(username, userId)
    }

    @DeleteMapping("/users/{userId}/add-friend")
    fun deleteFriendRequest(@RequestHeader username: String, @PathVariable userId: Long): ResponseEntity<String> {
        return followRequestService.deleteFollowRequest(username, userId)
    }

    @GetMapping("/me/friendRequests")
    fun getFriendRequests(@RequestHeader username: String) : ResponseEntity<String> {
        return followRequestService.getFollowRequests(username)
    }

    @DeleteMapping("/me/friendRequests/{friendRequestId}")
    fun respondToFriendRequest(@RequestHeader username: String, @PathVariable friendRequestId: Long, @RequestBody response: Boolean): ResponseEntity<String> {
        return followRequestService.approveFollowRequest(username, friendRequestId, response)
    }
}