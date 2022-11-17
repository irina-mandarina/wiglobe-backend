package com.example.demo.controllers

import com.example.demo.services.FollowRequestService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class FollowRequestController(val followRequestService: FollowRequestService) {
    @PostMapping("/users/{userId}/follow")
    fun sendFollowRequest(@RequestHeader username: String, @PathVariable userId: Long): ResponseEntity<String> {
        return followRequestService.sendFollowRequest(username, userId)
    }

    @DeleteMapping("/users/{userId}/follow")
    fun deleteFollowRequest(@RequestHeader username: String, @PathVariable userId: Long): ResponseEntity<String> {
        return followRequestService.deleteFollowRequest(username, userId)
    }

    @GetMapping("/me/followRequests")
    fun getFollowRequests(@RequestHeader username: String) : ResponseEntity<String> {
        return followRequestService.getFollowRequests(username)
    }

    @DeleteMapping("/me/followRequests/{followRequestId}")
    fun respondToFollowRequest(@RequestHeader username: String, @PathVariable followRequestId: Long, @RequestBody response: Boolean): ResponseEntity<String> {
        return followRequestService.approveFollowRequest(username, followRequestId, response)
    }
}