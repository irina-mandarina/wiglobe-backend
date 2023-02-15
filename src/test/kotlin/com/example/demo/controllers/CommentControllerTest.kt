package com.example.demo.controllers

import com.example.demo.models.requestModels.CommentRequest
import com.example.demo.models.responseModels.Comment
import com.example.demo.services.CommentService
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull
import io.mockk.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import kotlin.test.assertEquals
import kotlin.test.assertSame

class CommentControllerTest {
    private val sampleUsername = "kiril"
    private val sampleCommentId = 6L
    private val sampleJourneyId = 1L
    private val commentService = mockkClass(CommentService::class)
    private val commentController = CommentController(commentService)
    @Test
    fun commentServiceReturnsCommentsForJourney_WhenCallGetCommentsForJourney_CommentsFromServiceReturned() {
        // GIVEN:
        val com = mockkClass(Comment::class)
        every {com.content} returns "sample"
        val commentsFromService: List<Comment> = listOf(com)

        every { commentService.getCommentsForJourney(sampleUsername, sampleJourneyId) } returns ResponseEntity.status(HttpStatus.CREATED).body(
            commentsFromService
        )
        val commentController = CommentController(commentService)

        // WHEN:
        val commentsFromController = commentController.getCommentsForJourney(sampleUsername, sampleJourneyId)

        // THEN:
        assertNotNull(commentsFromController)
        assertSame(commentsFromController.body!![0], commentsFromService[0])
        assertEquals(commentsFromController.statusCode, HttpStatus.CREATED)
    }

    @Test
    fun commentServiceReturnsComment_WhenCommentJourneyCalled_CommentReturned() {
        // GIVEN:
        val sampleCommentRequest = mockkClass(CommentRequest::class)
        every {
            commentService.commentJourney(sampleUsername, sampleJourneyId, sampleCommentRequest)
        } returns ResponseEntity.ok().body(
            mockkClass(Comment::class)
        )

        // WHEN:
        val responseFromController = commentController.commentJourney(sampleUsername, sampleJourneyId, sampleCommentRequest)

        // THEN:
        assertNotNull(responseFromController)
        assertSame(responseFromController, commentService.commentJourney(sampleUsername, sampleJourneyId, sampleCommentRequest))
    }


    @Test
    fun commentServiceReturnsComment_WhenEditJourneyCalled_CommentReturned() {
        // GIVEN:
        val sampleCommentRequest = mockkClass(CommentRequest::class)
        every {
            commentService.editComment(sampleUsername, sampleJourneyId, sampleCommentRequest)
        } returns ResponseEntity.ok().body(
            mockkClass(Comment::class)
        )

        // WHEN:
        val responseFromController = commentController.editComment(sampleUsername, sampleCommentId, sampleJourneyId, sampleCommentRequest)

        // THEN:
        assertNotNull(responseFromController)
        assertSame(responseFromController, commentService.editComment(sampleUsername, sampleJourneyId, sampleCommentRequest))
    }

    @Test
    fun commentServiceReturnsBodyNull_WhenDeleteCommentCalled_BodyNullReturned() {
        // GIVEN:
        every {
            commentService.deleteComment(sampleUsername, sampleJourneyId, sampleCommentId)
        } returns ResponseEntity.ok().body(
            null
        )

        // WHEN:
        val responseFromController = commentController.deleteComment(sampleUsername, sampleJourneyId, sampleCommentId)

        // THEN:
        assertNotNull(responseFromController)
        assertSame(responseFromController, commentService.deleteComment(sampleUsername, sampleJourneyId, sampleCommentId))
        val result: ResponseEntity<String> = ResponseEntity.ok().body(null)
        assertEquals(responseFromController, result)
    }
}