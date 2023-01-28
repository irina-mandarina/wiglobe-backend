package com.example.demo.controllerTests;

import com.example.demo.controllers.FollowController
import com.example.demo.models.responseModels.UserDetails
import com.example.demo.services.FollowService;
import io.mockk.every
import io.mockk.mockkClass
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity
import kotlin.test.assertNotNull
import kotlin.test.assertSame

public class FollowControllerTest {
    val followService = mockkClass(FollowService::class)
    val followController = FollowController(followService)
    val sampleUsername = "kiril"
    val sampleUsername2 = "boiko"

    @Test
    fun followServiceReturnsFollowers_WhenGetFollowersCalled_FollowersReturned() {
        // GIVEN
        every {
            followService.getFollowers(sampleUsername)
        } returns ResponseEntity.ok().body(
            listOf(
                mockkClass(UserDetails::class)
            )
        )

        // WHEN
        val responseFromController = followController.getFollowers(sampleUsername)

        // THEN
        assertNotNull(responseFromController)
        assertSame(responseFromController, followService.getFollowers(sampleUsername))
    }


    @Test
    fun followServiceReturnsFollowing_WhenGetFollowingCalled_FollowingReturned() {
        // GIVEN
        every {
            followService.getFollowing(sampleUsername)
        } returns ResponseEntity.ok().body(
            listOf(
                mockkClass(UserDetails::class)
            )
        )

        // WHEN
        val responseFromController = followController.getFollowing(sampleUsername)

        // THEN
        assertNotNull(responseFromController)
        assertSame(responseFromController, followService.getFollowing(sampleUsername))
    }


    @Test
    fun followServiceReturnsFriends_WhenGetFriendsCalled_FriendsReturned() {
        // GIVEN
        every {
            followService.getFriends(sampleUsername)
        } returns ResponseEntity.ok().body(
            listOf(
                mockkClass(UserDetails::class)
            )
        )

        // WHEN
        val responseFromController = followController.getFriends(sampleUsername)

        // THEN
        assertNotNull(responseFromController)
        assertSame(responseFromController, followService.getFriends(sampleUsername))
    }


    @Test
    fun followServiceUnfollows_WhenUnfollowCalled_UserUnfollowed() {
        // GIVEN
        every {
            followService.unfollow(sampleUsername, sampleUsername2)
        } returns ResponseEntity.ok().body(
            null
        )

        // WHEN
        val responseFromController = followController.unfollow(sampleUsername, sampleUsername2)

        // THEN
        assertNotNull(responseFromController)
        assertSame(responseFromController, followService.unfollow(sampleUsername, sampleUsername2))
    }
}
