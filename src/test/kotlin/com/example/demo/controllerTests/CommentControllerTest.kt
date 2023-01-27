package com.example.demo.controllerTests

import com.example.demo.controllers.CommentController
import com.example.demo.models.responseModels.Comment
import com.example.demo.models.responseModels.UserNames
import com.example.demo.services.CommentService
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull
import io.mockk.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.math.BigDecimal
import kotlin.test.assertEquals
import kotlin.test.assertSame

class CommentControllerTest {
    private val username = "kiril"
    private val journeyId = 1L
    @Test
    fun commentServiceReturnsCommentsForJourney_WhenCallGetCommentsForJourney_CommentsFromServiceReturned() {
        // GIVEN:
        val commentService = mockkClass(CommentService::class)
        val com = mockkClass(Comment::class)
        every {com.content} returns "sample"
        val commentsFromService: List<Comment> = listOf(com)

        every { commentService.getCommentsForJourney(username, journeyId) } returns ResponseEntity.status(HttpStatus.CREATED).body(
            commentsFromService
        )
        val commentController = CommentController(commentService)

        // WHEN:
        val commentsFromController = commentController.getCommentsForJourney(username, journeyId)

        // THEN:
        assertNotNull(commentsFromController)
        assertSame(commentsFromController.body!![0], commentsFromService[0])
        assertEquals(commentsFromController.statusCode, HttpStatus.CREATED)

    }
}