package com.example.demo.controllers

import com.example.demo.models.responseModels.Follow
import com.example.demo.models.responseModels.FollowRequest
import com.example.demo.services.FollowRequestService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
class FollowRequestController(private val followRequestService: FollowRequestService) {
    @PostMapping("/users/{username}/follow")
    fun sendFollowRequest(@RequestHeader username: String, @PathVariable("username") receiverUsername: String): ResponseEntity<FollowRequest> {
        return followRequestService.sendFollowRequest(username, receiverUsername)
    }

    @DeleteMapping("/users/{username}/follow")
    fun deleteFollowRequest(@RequestHeader username: String, @PathVariable("username") receiverUsername: String): ResponseEntity<String> {
        return followRequestService.deleteFollowRequest(username, receiverUsername)
    }

    @GetMapping("/users/me/follow-requests")
    fun getFollowRequests(@RequestHeader username: String) : ResponseEntity<List<FollowRequest>> {
        return followRequestService.getFollowRequests(username)
    }

    @DeleteMapping("/users/me/follow-requests/{username}")
    fun respondToFollowRequest(@RequestHeader username: String, @PathVariable("username") requester: String,
                               @RequestBody response: Boolean): ResponseEntity<Follow> {
        return followRequestService.approveFollowRequest(username, requester, response)
    }
}