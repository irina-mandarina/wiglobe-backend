package com.example.demo.services

import com.example.demo.entities.FollowRequestEntity
import com.example.demo.models.responseModels.FollowRequest
import com.example.demo.models.responseModels.Follow
import com.example.demo.models.responseModels.UserNames
import com.example.demo.repositories.FollowRequestRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.LocalDateTime

@Service
class FollowRequestService(private val followRequestRepository: FollowRequestRepository,
                           private val followService: FollowService, private val userService: UserService) {
    fun followRequestFromEntity(followRequestEntity: FollowRequestEntity): FollowRequest {
        return FollowRequest(
            userService.userNames(followRequestEntity.receiver),
            userService.userNames(followRequestEntity.requester),
            followRequestEntity.requestDate,
        )
    }

    fun sendFollowRequest(username: String, receiverUsername: String): ResponseEntity<FollowRequest> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(
                "message", "Username does not exist"
            ).body(null)
        }

        if (findByReceiverUsernameAndAndRequesterUsername(receiverUsername, username) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).header(
                "message","$username already requested to follow $receiverUsername"
            ).body(null)
        }

        val receiver = userService.findUserByUsername(receiverUsername)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message", "User $receiverUsername does not exist"
                    ).body(null)
        val followRequest = FollowRequestEntity()

        followRequest.receiver = receiver
        followRequest.requester = userService.findUserByUsername(username)!!
        followRequest.requestDate = Timestamp.valueOf(LocalDateTime.now())

        followRequestRepository.save(followRequest)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            followRequestFromEntity(followRequest)
        )
    }

    fun deleteFollowRequest(username: String, receiverUsername: String): ResponseEntity<String> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(
                "message", "Username does not exist"
            ).body(null)
        }

        val request = findByReceiverUsernameAndAndRequesterUsername(receiverUsername, username)
            ?: return ResponseEntity.status(HttpStatus.CREATED).header(
                "message", "$username has not requested to follow $receiverUsername"
            ).body(null)

        followRequestRepository.delete(request)
        return ResponseEntity.ok().header(
            "message","Deleted $username's request to follow $receiverUsername"
        ).body(null)
    }

    fun getFollowRequests(username: String): ResponseEntity<List<FollowRequest>> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(
                "message", "Username does not exist"
            ).body(null)
        }

        val response = findAllByReceiverUsername(username).map {
            FollowRequest (
                UserNames(
                    it.requester.username,
                    it.requester.firstName,
                    it.requester.lastName
                ),
                UserNames(
                    it.receiver.username,
                    it.receiver.firstName,
                    it.receiver.lastName
                ),
                it.requestDate
            )
        }
        return ResponseEntity.ok().body(
            findAllByReceiverUsername(username).map {
                followRequestFromEntity( it )
            }
        )
    }

    fun approveFollowRequest(username: String, requesterUsername: String, response: Boolean): ResponseEntity<Follow> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(
                "message","Username does not exist"
            ).body(null)
        }

        val request = findByReceiverUsernameAndAndRequesterUsername(username, requesterUsername)
            ?: return ResponseEntity.status(HttpStatus.CREATED).header(
                "message","$username has not requested to follow that user"
            ).body(null)

        if (response) {
            val follow = followService.saveFollow(request)
                ?: return ResponseEntity.status(HttpStatus.NOT_MODIFIED).header(
                    "message","Follow is null: tackle later"
                ).body(null)

            followRequestRepository.delete(request)
            return ResponseEntity.ok().body(
                Follow(
                    UserNames(
                        follow.follower.username, follow.follower.firstName, follow.follower.lastName
                    ),
                    UserNames(
                        follow.followed.username, follow.followed.firstName, follow.followed.lastName
                    ),
                    follow.followDate
                )
            )
        }
        else {
            followRequestRepository.delete(request)
            return ResponseEntity.ok().header(
                "message","$username rejected $requesterUsername's follow request"
            ).body(null)
        }

    }

    fun findByReceiverUsernameAndAndRequesterUsername(receiver: String, requester: String): FollowRequestEntity? {
        return followRequestRepository.findByReceiverUsernameAndAndRequesterUsername(receiver, requester)
    }

    fun findAllByReceiverUsername(receiverUsername: String): List<FollowRequestEntity> {
        return followRequestRepository.findAllByReceiverUsername(receiverUsername)
    }
}