package com.example.demo.services

import com.example.demo.entities.CommentEntity
import com.example.demo.entities.JourneyEntity
import com.example.demo.entities.UserEntity
import com.example.demo.repositories.CommentRepository
import com.example.demo.models.responseModels.Comment
import com.example.demo.models.requestModels.CommentRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class CommentService(private val journeyService: JourneyService, private val userService: UserService,
                     private val commentRepository: CommentRepository, private val notificationService: NotificationService, interestsService: InterestsService) {

    fun commentFromEntity(commentEntity: CommentEntity): Comment {
        return Comment(
            commentEntity.id,
            commentEntity.journey.id,
            userService.userNames(commentEntity.user),
            commentEntity.postedOn,
            commentEntity.content
        )
    }
    fun getCommentsForJourney(username: String, journeyId: Long): ResponseEntity<List<Comment>> {
        if (!journeyService.journeyWithIdExists(journeyId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
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
        if (!journeyService.journeyWithIdExists(journeyId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null)
        }

        var comment = CommentEntity(commentRequest, journeyService.findJourneyById(journeyId)!!,
            userService.findUserByUsername(username)!!)

        comment = commentRepository.save(comment)
        notificationService.notifyForComment(comment,
            "$username commented on your journey")
        return ResponseEntity.status(HttpStatus.CREATED).body(
            commentFromEntity(comment)
        )
    }

    fun editComment(username: String, commentId: Long, commentRequest: CommentRequest): ResponseEntity<Comment> {
        if (!commentWithIdExists(commentId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }

        val comment = findCommentById(commentId)!!

        if (comment.user.username != username) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
        }

        comment.content = commentRequest.content

        commentRepository.save(comment)

        return ResponseEntity.ok().body(
            commentFromEntity(comment)
        )
    }

    fun deleteComment(username: String, journeyId: Long, commentId: Long): ResponseEntity<String> {
        if (!commentWithIdExists(commentId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }

        val comment = findCommentById(commentId)!!

        if (comment.user.username != username) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null)
        }

//        if (intere)

        notificationService.deleteNotificationForComment(comment)
        commentRepository.delete(comment)

        return ResponseEntity.ok().body(null)
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

    fun findAllByUserAndIdGreaterThan(user: UserEntity, id: Long): List<CommentEntity> {
        return commentRepository.findAllByUserAndIdGreaterThan(user, id)
    }

}