package com.example.demo.controllers

import com.example.demo.services.FollowService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

class FollowController(val followService: FollowService) {

//    @GetMapping("/users/{userId}/friendship")
//    fun getFriendshipStatus(@RequestHeader username: String, @PathVariable userId: Long): ResponseEntity<String> {
//        return friendshipService.getFriendshipStatus(username, userId)
//    }

//    @DeleteMapping("/users/{userId}/friendship")
//    fun endFriendship(@RequestHeader username: String, @PathVariable userId: Long): ResponseEntity<String> {
//        return friendshipService.endFriendship(username, userId)
//    }
}