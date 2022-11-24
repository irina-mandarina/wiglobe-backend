package com.example.demo.controllers

import com.example.demo.requestEntities.GetComment
import com.example.demo.requestEntities.PostComment
import com.example.demo.services.CommentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class CommentController(val commentService: CommentService) {
    @GetMapping("/journeys/{journeyId}/comments")
    fun getCommentsForJourney(@RequestHeader username: String, @PathVariable journeyId: Long): ResponseEntity<String> {
        return commentService.getCommentsForJourney(username, journeyId)
    }

    @PostMapping("/journeys/{journeyId}/comments")
    fun commentJourney(@RequestHeader username: String, @PathVariable journeyId: Long, @RequestBody commentRequest: PostComment): ResponseEntity<String> {
        return commentService.commentJourney(username, journeyId, commentRequest)
    }

    @PutMapping("/journeys/{journeyId}/comments/{commentId}")
    fun editComment(@RequestHeader username: String, @PathVariable journeyId: Long, @PathVariable commentId: Long, @RequestBody commentRequest: PostComment): ResponseEntity<String> {
        return commentService.editComment(username, commentId, commentRequest)
    }

    @DeleteMapping("/journeys/{journeyId}/comments/{commentId}")
    fun deleteComment(@RequestHeader username: String, @PathVariable journeyId: Long, @PathVariable commentId: Long): ResponseEntity<String> {
        return commentService.deleteComment(username, commentId)
    }
}