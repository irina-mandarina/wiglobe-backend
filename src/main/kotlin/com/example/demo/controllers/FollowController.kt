package com.example.demo.controllers

import com.example.demo.services.FollowService
import com.vader.sentiment.analyzer.SentimentAnalyzer
import org.springframework.web.bind.annotation.*


class FollowController(val followService: FollowService) {

//    @GetMapping("/users/{userId}/friendship")
//    fun getFriendshipStatus(@RequestHeader username: String, @PathVariable userId: Long): ResponseEntity<String> {
//        return friendshipService.getFriendshipStatus(username, userId)
//    }
    var sentimentAnalyzer: SentimentAnalyzer = SentimentAnalyzer()
    sentimentAnalyzer.getScoresFor("sfgsg ")


//    @DeleteMapping("/users/{userId}/friendship")
//    fun endFriendship(@RequestHeader username: String, @PathVariable userId: Long): ResponseEntity<String> {
//        return friendshipService.endFriendship(username, userId)
//    }
}