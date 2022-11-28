package com.example.demo.services

import com.example.demo.entities.FollowRequest
import com.example.demo.models.responseModels.FollowRequestResponse
import com.example.demo.models.responseModels.FollowResponse
import com.example.demo.models.responseModels.UserNamesResponse
import com.example.demo.repositories.FollowRequestRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.LocalDateTime

@Service
class FollowRequestService(private val followRequestRepository: FollowRequestRepository,
                           private val followService: FollowService, private val userService: UserService) {
    fun sendFollowRequest(username: String, receiverUsername: String): ResponseEntity<String> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        if (findByReceiverUsernameAndAndRequesterUsername(receiverUsername, username) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("$username already requested to follow $receiverUsername")
        }

        val receiver = userService.findUserByUsername(receiverUsername)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User $receiverUsername does not exist")
        val followRequest = FollowRequest()

        followRequest.receiver = receiver
        followRequest.requester = userService.findUserByUsername(username)!!
        followRequest.requestDate = Timestamp.valueOf(LocalDateTime.now())

        followRequestRepository.save(followRequest)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            Json.encodeToString(
                FollowRequestResponse(
                    UserNamesResponse(
                        followRequest.requester.username,
                        followRequest.requester.firstName,
                        followRequest.requester.lastName
                    ),
                    UserNamesResponse(
                        followRequest.receiver.username,
                        followRequest.receiver.firstName,
                        followRequest.receiver.lastName
                    ),
                    followRequest.requestDate
                )
            )
        )
    }

    fun deleteFollowRequest(username: String, receiverUsername: String): ResponseEntity<String> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        val request = findByReceiverUsernameAndAndRequesterUsername(receiverUsername, username)
            ?: return ResponseEntity.status(HttpStatus.CREATED).body("$username has not requested to follow $receiverUsername")

        followRequestRepository.delete(request)
        return ResponseEntity.ok().body("Deleted $username's request to follow $receiverUsername")
    }

    fun getFollowRequests(username: String): ResponseEntity<String> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        val requestsResponse = findAllByReceiverUsername(username).map {
            FollowRequestResponse (
                UserNamesResponse(
                    it.requester.username,
                    it.requester.firstName,
                    it.requester.lastName
                ),
                UserNamesResponse(
                    it.receiver.username,
                    it.receiver.firstName,
                    it.receiver.lastName
                ),
                it.requestDate
            )
        }
        return ResponseEntity.ok().body(
            Json.encodeToString(
                requestsResponse
            )
        )
    }

    fun approveFollowRequest(username: String, requesterUsername: String, response: Boolean): ResponseEntity<String> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        val request = findByReceiverUsernameAndAndRequesterUsername(username, requesterUsername)
            ?: return ResponseEntity.status(HttpStatus.CREATED).body("$username has not requested to follow that user")

        if (response) {
            val follow = followService.saveFollow(request)
                ?: return ResponseEntity.status(HttpStatus.NOT_MODIFIED).
                body("Follow is null: tackle later")

            followRequestRepository.delete(request)
            return ResponseEntity.ok().body(
                Json.encodeToString(
                    FollowResponse(
                        UserNamesResponse(
                            follow.follower.username, follow.follower.firstName, follow.follower.lastName
                        ),
                        UserNamesResponse(
                            follow.followed.username, follow.followed.firstName, follow.followed.lastName
                        ),
                        follow.followDate
                    )
                )
            )
        }
        else {
            followRequestRepository.delete(request)
            return ResponseEntity.ok().body("$username rejected $requesterUsername's follow request")
        }

    }

    fun findByReceiverUsernameAndAndRequesterUsername(receiver: String, requester: String): FollowRequest? {
        return followRequestRepository.findByReceiverUsernameAndAndRequesterUsername(receiver, requester)
    }

    fun findAllByReceiverUsername(receiverUsername: String): List<FollowRequest> {
        return followRequestRepository.findAllByReceiverUsername(receiverUsername)
    }
}