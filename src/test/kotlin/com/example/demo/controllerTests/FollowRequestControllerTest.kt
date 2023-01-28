package com.example.demo.controllerTests

import com.example.demo.controllers.FollowRequestController
import com.example.demo.models.requestModels.ActivityRequest
import com.example.demo.models.responseModels.Activity
import com.example.demo.models.responseModels.Follow
import com.example.demo.models.responseModels.FollowRequest
import com.example.demo.services.FollowRequestService
import io.mockk.every
import io.mockk.mockkClass
import org.junit.jupiter.api.Test
import org.springframework.http.ResponseEntity
import kotlin.test.assertNotNull
import kotlin.test.assertSame

class FollowRequestControllerTest {
    val followRequestService = mockkClass(FollowRequestService::class)
    val followRequestController = FollowRequestController(followRequestService)
    val sampleUsername = "kiril"
    val sampleUsername2 = "boiko"

    @Test
    fun followRequestServiceSendsFollowRequests_WhenSendFollowRequestCalled_FollowRequestSent() {
        // GIVEN
        every {
            followRequestService.sendFollowRequest(sampleUsername, sampleUsername2)
        } returns ResponseEntity.ok().body(
            mockkClass(FollowRequest::class)
        )

        // WHEN
        val responseFromController = followRequestController.sendFollowRequest(sampleUsername, sampleUsername2)

        // THEN
        assertNotNull(responseFromController)
        assertSame(responseFromController, followRequestService.sendFollowRequest(sampleUsername, sampleUsername2))
    }

    @Test
    fun followRequestServiceDeletesFollowRequest_WhenDeleteFollowRequestCalled_FollowRequestDeleted() {
        // GIVEN
        every {
            followRequestService.deleteFollowRequest(sampleUsername, sampleUsername2)
        } returns ResponseEntity.ok().body(
            null
        )

        // WHEN
        val responseFromController = followRequestController.deleteFollowRequest(sampleUsername, sampleUsername2)

        // THEN
        assertNotNull(responseFromController)
        assertSame(responseFromController, followRequestService.deleteFollowRequest(sampleUsername, sampleUsername2))
    }

    @Test
    fun followRequestServiceReturnsReceivedFollowRequests_WhenGetReceivedFollowRequestsCalled_ReceivedFollowRequestsReturned() {
        // GIVEN
        every {
            followRequestService.getReceivedFollowRequests(sampleUsername)
        } returns ResponseEntity.ok().body(
            listOf(
                mockkClass(FollowRequest::class)
            )
        )

        // WHEN
        val responseFromController = followRequestController.getReceivedFollowRequests(sampleUsername)

        // THEN
        assertNotNull(responseFromController)
        assertSame(responseFromController, followRequestService.getReceivedFollowRequests(sampleUsername))
    }
    @Test
    fun followRequestServiceReturnsSentFollowRequests_WhenGetSentFollowRequests_SentFollowRequestsReturned() {
        // GIVEN
        every {
            followRequestService.getSentFollowRequests(sampleUsername)
        } returns ResponseEntity.ok().body(
            listOf(
                mockkClass(FollowRequest::class)
            )
        )

        // WHEN
        val responseFromController = followRequestController.getSentFollowRequests(sampleUsername)

        // THEN
        assertNotNull(responseFromController)
        assertSame(responseFromController, followRequestService.getSentFollowRequests(sampleUsername))
    }


    @Test
    fun followRequestServiceRespondsToFollowRequest_WhenRespondToFollowRequestCalled_FollowRequestIsRespondedTo() {
        // GIVEN
        every {
            followRequestService.approveFollowRequest(sampleUsername, sampleUsername2, true)
        } returns ResponseEntity.ok().body(
            mockkClass(Follow::class)
        )

        // WHEN
        val responseFromController = followRequestController.respondToFollowRequest(sampleUsername, sampleUsername2, true)

        // THEN
        assertNotNull(responseFromController)
        assertSame(responseFromController, followRequestService.approveFollowRequest(sampleUsername, sampleUsername2, true))
    }

}