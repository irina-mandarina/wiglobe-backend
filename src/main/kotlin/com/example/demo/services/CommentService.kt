package com.example.demo.services

import com.example.demo.entities.Comment
import com.example.demo.entities.Journey
import com.example.demo.repositories.CommentRepository
import com.example.demo.models.responseModels.CommentResponse
import com.example.demo.models.requestModels.CommentRequest
import com.example.demo.models.responseModels.UserNamesResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val journeyService: JourneyService, private val userService: UserService,
    val commentRepository: CommentRepository) {
    fun getCommentsForJourney(username: String, journeyId: Long): ResponseEntity<String> {
        if (username.isBlank()) {
            return ResponseEntity.badRequest().body("Missing request parameter: username")
        }

        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        if (!journeyService.journeyWithIdExists(journeyId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journey does not exist")
        }

        val comments: List<Comment> = findCommentsByJourney(journeyService.findJourneyById(journeyId)!!)
        val getComments: List<CommentResponse> = comments.map {
            CommentResponse(
                it.id!!,
                UserNamesResponse(
                    it.user.username, it.user.firstName, it.user.lastName
                ),
                it.datePosted,
                it.content
            )
        }

        return ResponseEntity.ok().body(
            Json.encodeToString(
                getComments
            )
        )
    }
    fun commentJourney(
        username: String,
        journeyId: Long,
        commentRequest: CommentRequest
    ): ResponseEntity<String> {

        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        if (!journeyService.journeyWithIdExists(journeyId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journey does not exist")
        }

        val comment = Comment(commentRequest, journeyService.findJourneyById(journeyId)!!,
            userService.findUserByUsername(username)!!)

        commentRepository.save(comment)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            Json.encodeToString(
                CommentResponse(
                    comment.id!!,
                    UserNamesResponse(
                        comment.user.username, comment.user.firstName, comment.user.lastName
                    ),
                    comment.datePosted,
                    comment.content
                )
            )
        )
    }

    fun editComment(username: String, commentId: Long, commentRequest: CommentRequest): ResponseEntity<String> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        if (!commentWithIdExists(commentId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment does not exist")
        }

        val comment = findCommentById(commentId)!!

        if (comment.user.username != username) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                "Comment does not belong to user with username: $username"
            )
        }

        comment.content = commentRequest.content

        commentRepository.save(comment)

        return ResponseEntity.ok().body(
            Json.encodeToString(
                CommentResponse(
                    comment.id!!,
                    UserNamesResponse(
                        comment.user.username, comment.user.firstName, comment.user.lastName
                    ),
                    comment.datePosted,
                    comment.content
                )
            )
        )
    }

    fun deleteComment(username: String, commentId: Long): ResponseEntity<String> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        if (!commentWithIdExists(commentId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment does not exist")
        }

        val comment = findCommentById(commentId)!!

        if (comment.user.username != username) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                "Comment does not belong to user with username: $username"
            )
        }

        commentRepository.delete(comment)

        return ResponseEntity.ok().body("Successfully deleted a comment.")
    }

    fun commentWithIdExists(commentId: Long): Boolean {
        return (findCommentById(commentId) != null)
    }

    fun findCommentById(id: Long): Comment? {
        return commentRepository.findCommentById(id)
    }

    fun findCommentsByJourney(journey: Journey): List<Comment> {
        return commentRepository.findCommentsByJourney(journey)
    }

}