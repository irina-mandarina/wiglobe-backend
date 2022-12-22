package com.example.demo.controllers

import com.example.demo.models.responseModels.UserDetails
import com.example.demo.services.FollowService
import com.vader.sentiment.analyzer.SentimentAnalyzer
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


class FollowController(val followService: FollowService) {

    @GetMapping("/users/{username}/followers")
    fun getFollowers(@RequestHeader username: String): ResponseEntity<List<UserDetails>> {
        return followService.getFollowers(username)
    }

    @GetMapping("/users/{username}/following")
    fun getFollowing(@RequestHeader username: String): ResponseEntity<List<UserDetails>> {
        return followService.getFollowing(username)
    }

    @GetMapping("/users/{username}/friends")
    fun getFriends(@RequestHeader username: String): ResponseEntity<List<UserDetails>> {
        return followService.getFriends(username)
    }
}