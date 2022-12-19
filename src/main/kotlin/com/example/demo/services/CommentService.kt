package com.example.demo.services

import com.example.demo.entities.CommentEntity
import com.example.demo.entities.JourneyEntity
import com.example.demo.entities.UserEntity
import com.example.demo.repositories.CommentRepository
import com.example.demo.models.responseModels.Comment
import com.example.demo.models.requestModels.CommentRequest
import com.example.demo.models.responseModels.UserNames
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val journeyService: JourneyService, private val userService: UserService,
    private val commentRepository: CommentRepository) {

    fun commentFromEntity(commentEntity: CommentEntity): Comment {
        return Comment(
            commentEntity.id!!,
            userService.userNames(commentEntity.user),
            commentEntity.datePosted,
            commentEntity.content
        )
    }
    fun getCommentsForJourney(username: String, journeyId: Long): ResponseEntity<List<Comment>> {
        if (username.isBlank()) {
            return ResponseEntity.badRequest().header(
                "message","Missing request parameter: username")
                .body(null)
        }

        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(
                "message","Username does not exist")
                .body(null)
        }

        if (!journeyService.journeyWithIdExists(journeyId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message","Journey does not exist")
                .body(null)
        }

        val comments: List<CommentEntity> = findCommentsByJourney(journeyService.findJourneyById(journeyId)!!)

        return ResponseEntity.ok().body(
                comments.map {
                    commentFromEntity(it)
                }
        )
    }
    fun commentJourney(
        username: String,
        journeyId: Long,
        commentRequest: CommentRequest
    ): ResponseEntity<Comment> {

        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(
                "message","Username does not exist")
                .body(null)
        }

        if (!journeyService.journeyWithIdExists(journeyId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message","Journey does not exist")
                .body(null)
        }

        val comment = CommentEntity(commentRequest, journeyService.findJourneyById(journeyId)!!,
            userService.findUserByUsername(username)!!)

        commentRepository.save(comment)

//        val calc = JourneyScoreCalculator()
//        calc.calculateScoreForJourney()

        return ResponseEntity.status(HttpStatus.CREATED).body(
            commentFromEntity(comment)
        )
    }

    fun editComment(username: String, commentId: Long, commentRequest: CommentRequest): ResponseEntity<Comment> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(
                "message","Username does not exist")
                .body(null)
        }

        if (!commentWithIdExists(commentId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message","Comment does not exist")
                .body(null)
        }

        val comment = findCommentById(commentId)!!

        if (comment.user.username != username) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(
                "message","Comment does not belong to user with username: $username")
                .body(null)
        }

        comment.content = commentRequest.content

        commentRepository.save(comment)

        return ResponseEntity.ok().body(
            commentFromEntity(comment)
        )
    }

    fun deleteComment(username: String, commentId: Long): ResponseEntity<String> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(
                "message","Username does not exist")
                .body(null)
        }

        if (!commentWithIdExists(commentId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message","Comment does not exist")
                .body(null)
        }

        val comment = findCommentById(commentId)!!

        if (comment.user.username != username) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(
                "message", "Comment does not belong to user with username: $username")
                .body(null)
        }

        commentRepository.delete(comment)

        return ResponseEntity.ok().header(
            "message","Successfully deleted a comment.")
            .body(null)
    }

    fun commentWithIdExists(commentId: Long): Boolean {
        return (findCommentById(commentId) != null)
    }

    fun findCommentById(id: Long): CommentEntity? {
        return commentRepository.findCommentById(id)
    }

    fun findCommentsByJourney(journey: JourneyEntity): List<CommentEntity> {
        return commentRepository.findCommentsByJourney(journey)
    }

    fun findAllByUser(user: UserEntity): List<CommentEntity> {
        return commentRepository.findAllByUser(user)
    }

}