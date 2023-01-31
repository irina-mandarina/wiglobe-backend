package com.example.demo.controllers

import com.example.demo.models.responseModels.Follow
import com.example.demo.models.responseModels.FollowRequest
import com.example.demo.services.FollowRequestService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("http://localhost:3000")
class FollowRequestController(private val followRequestService: FollowRequestService) {
    @PostMapping("/users/{username}/follow-requests")
    fun sendFollowRequest(@RequestAttribute username: String, @PathVariable("username") receiverUsername: String): ResponseEntity<FollowRequest> {
        return followRequestService.sendFollowRequest(username, receiverUsername)
    }

    @DeleteMapping("/users/{username}/follow-requests")
    fun deleteFollowRequest(@RequestAttribute username: String, @PathVariable("username") receiverUsername: String): ResponseEntity<String> {
        return followRequestService.deleteFollowRequest(username, receiverUsername)
    }

    @GetMapping("/users/{username}/follow-requests/received")
    fun getReceivedFollowRequests(@RequestAttribute username: String) : ResponseEntity<List<FollowRequest>> {
        return followRequestService.getReceivedFollowRequests(username)
    }

    @GetMapping("/users/{username}/follow-requests/sent")
    fun getSentFollowRequests(@RequestAttribute username: String) : ResponseEntity<List<FollowRequest>> {
        return followRequestService.getSentFollowRequests(username)
    }

    @DeleteMapping("/users/{username}/follow-requests/{requesterUsername}")
    fun respondToFollowRequest(@RequestAttribute username: String, @PathVariable("requesterUsername") requester: String,
                               @RequestParam response: Boolean): ResponseEntity<Follow> {
        return followRequestService.approveFollowRequest(username, requester, response)
    }
}