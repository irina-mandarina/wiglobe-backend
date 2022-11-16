package com.example.demo.services

import com.example.demo.entities.Comment
import com.example.demo.entities.Journey
import com.example.demo.requestEntities.CommentRequest
import org.springframework.http.ResponseEntity

interface CommentService {
    fun commentJourney(username: String, journeyId: Long, commentRequest: CommentRequest): ResponseEntity<String>
    fun deleteComment(username: String, commentId: Long): ResponseEntity<String>
    fun getCommentsForJourney(username: String, journeyId: Long): ResponseEntity<String>
    fun commentWithIdExists(commentId: Long): Boolean
    fun findCommentById(id: Long): Comment?
    fun findCommentsByJourney(journey: Journey): List<Comment>
    fun editComment(username: String, commentId: Long, commentRequest: CommentRequest): ResponseEntity<String>
}