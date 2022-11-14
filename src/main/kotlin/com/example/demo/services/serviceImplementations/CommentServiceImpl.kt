package com.example.demo.services.serviceImplementations

import com.example.demo.services.CommentService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class CommentServiceImpl: CommentService {
    override fun getCommentsForJourney(username: String, journeyId: String): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun commentJourney(username: String, journeyId: String): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun editComment(username: String, journeyId: Long, commentId: Long): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun deleteComment(username: String, journeyId: Long, commentId: Long): ResponseEntity<String> {
        TODO("Not yet implemented")
    }
}