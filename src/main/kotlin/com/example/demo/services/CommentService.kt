package com.example.demo.services

import org.springframework.http.ResponseEntity

interface CommentService {
    abstract fun getCommentsForJourney(username: String, journeyId: String): ResponseEntity<String>
    abstract fun commentJourney(username: String, journeyId: String): ResponseEntity<String>
    abstract fun editComment(username: String, journeyId: Long, commentId: Long): ResponseEntity<String>
    abstract fun deleteComment(username: String, journeyId: Long, commentId: Long): ResponseEntity<String>
}