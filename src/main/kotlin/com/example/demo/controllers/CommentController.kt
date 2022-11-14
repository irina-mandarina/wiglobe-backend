package com.example.demo.controllers

import com.example.demo.services.CommentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class CommentController {
    private val commentService: CommentService = TODO()

    @GetMapping("/journeys/{journeyId}/comments")
    fun getCommentsForJourney(@RequestHeader username: String, @PathVariable journeyId: String): ResponseEntity<String> {
        return commentService.getCommentsForJourney(username, journeyId)
    }

    @PostMapping("/journeys/{journeyId}/comments")
    fun commentJourney(@RequestHeader username: String, @PathVariable journeyId: String): ResponseEntity<String> {
        return commentService.commentJourney(username, journeyId)
    }

    @PutMapping("/journeys/{journeyId}/comments/{commentId}")
    fun editComment(@RequestHeader username: String, @PathVariable journeyId: Long, @PathVariable commentId: Long): ResponseEntity<String> {
        return commentService.editComment(username, journeyId, commentId)
    }

    @DeleteMapping("/journeys/{journeyId}/comments/{commentId}")
    fun deleteComment(@RequestHeader username: String, @PathVariable journeyId: Long, @PathVariable commentId: Long): ResponseEntity<String> {
        return commentService.deleteComment(username, journeyId, commentId)
    }
}