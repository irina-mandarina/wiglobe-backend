package com.example.demo.controllers

import com.example.demo.models.responseModels.UserDetails
import com.example.demo.services.FollowService
import com.vader.sentiment.analyzer.SentimentAnalyzer
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
class FollowController(private val followService: FollowService) {

    @GetMapping("/users/{username}/followers")
    fun getFollowers(@PathVariable username: String): ResponseEntity<List<UserDetails>> {
        return followService.getFollowers(username)
    }

    @GetMapping("/users/{username}/following")
    fun getFollowing(@PathVariable username: String): ResponseEntity<List<UserDetails>> {
        return followService.getFollowing(username)
    }

    @GetMapping("/users/{username}/friends")
    fun getFriends(@PathVariable username: String): ResponseEntity<List<UserDetails>> {
        return followService.getFriends(username)
    }

    @DeleteMapping("/users/{username}/followers/{usernameBeingFollowed}")
    fun unfollow(@RequestHeader username: String, @PathVariable("usernameBeingFollowed") usernameBeingFollowed: String): ResponseEntity<String> {
        return followService.unfollow(username, usernameBeingFollowed)
    }
}