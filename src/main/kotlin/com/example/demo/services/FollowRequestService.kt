package com.example.demo.services

import com.example.demo.entities.FollowRequestEntity
import com.example.demo.models.responseModels.FollowRequest
import com.example.demo.models.responseModels.Follow
import com.example.demo.models.responseModels.UserNames
import com.example.demo.repositories.FollowRequestRepository
import com.example.demo.types.ProfilePrivacy
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.LocalDateTime

@Service
class FollowRequestService(private val followRequestRepository: FollowRequestRepository,
                           private val followService: FollowService, private val userService: UserService, private val notificationService: NotificationService) {
    private fun followRequestFromEntity(followRequestEntity: FollowRequestEntity): FollowRequest {
        return FollowRequest(
            userService.userNames(followRequestEntity.requester),
            userService.userNames(followRequestEntity.receiver),
            followRequestEntity.requestDate,
        )
    }

    fun sendFollowRequest(username: String, receiverUsername: String): ResponseEntity<FollowRequest?> {
        if (!userService.userWithUsernameExists(receiverUsername)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
        }

        if (findByReceiverUsernameAndRequesterUsername(receiverUsername, username) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(null)
        }

        val receiver = userService.findUserByUsername(receiverUsername)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)

        // check if not private
        if (receiver.userDetails!!.privacy == ProfilePrivacy.PUBLIC) {
            // if the user's profile is public it's not needed to send a
            // request so the sender automatically follows the receiver
            followService.saveFollow(userService.findUserByUsername(username)!!, receiver)
            return ResponseEntity.status(HttpStatus.CREATED).body(null)
        }

        var followRequest = FollowRequestEntity()

        followRequest.receiver = receiver
        val requester = userService.findUserByUsername(username)!!
        followRequest.requester = requester
        followRequest.requestDate = Timestamp.valueOf(LocalDateTime.now())

        followRequest = followRequestRepository.save(followRequest)

        notificationService.notifyForFollowRequest(followRequest,
            "$username sent you a follow request")

        return ResponseEntity.status(HttpStatus.CREATED).body(
            followRequestFromEntity(followRequest)
        )
    }

    fun getSentFollowRequests(username: String): ResponseEntity<List<FollowRequest>> {
        return ResponseEntity.ok().body(
            findAllByRequesterUsername(username).map {
                followRequestFromEntity( it )
            }
        )
    }

    fun deleteFollowRequest(username: String, receiverUsername: String): ResponseEntity<String> {
        val request = findByReceiverUsernameAndRequesterUsername(receiverUsername, username)
            ?: return ResponseEntity.status(HttpStatus.CREATED).body(null)

        notificationService.deleteNotificationForFollowRequest(request)
        followRequestRepository.delete(request)
        return ResponseEntity.ok().body(null)
    }

    fun getReceivedFollowRequests(username: String): ResponseEntity<List<FollowRequest>> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(
                "message", "Username does not exist"
            ).body(null)
        }

        return ResponseEntity.ok().body(
            findAllByReceiverUsername(username).map {
                followRequestFromEntity( it )
            }
        )
    }

    fun approveFollowRequest(username: String, requesterUsername: String, response: Boolean): ResponseEntity<Follow> {
        val request = findByReceiverUsernameAndRequesterUsername(username, requesterUsername)
            ?: return ResponseEntity.status(HttpStatus.CREATED).body(null)

        if (response) {
            val follow = followService.saveFollow(request)
                ?: return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null)

            followRequestRepository.delete(request)

            notificationService.notifyForFollowRequestResponse(request,
                "$username approved your follow request"
            )
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
            notificationService.notifyForFollowRequestResponse(request,
                    "$username declined your follow request"
            )
            followRequestRepository.delete(request)
            return ResponseEntity.ok().header(
                "message","$username rejected $requesterUsername's follow request"
            ).body(null)
        }

    }

    fun findByReceiverUsernameAndRequesterUsername(receiver: String, requester: String): FollowRequestEntity? {
        return followRequestRepository.findByReceiverUsernameAndRequesterUsername(receiver, requester)
    }

    fun findAllByReceiverUsername(receiverUsername: String): List<FollowRequestEntity> {
        return followRequestRepository.findAllByReceiverUsername(receiverUsername)
    }

    fun findAllByRequesterUsername(requesterUsername: String): List<FollowRequestEntity> {
        return followRequestRepository.findAllByRequesterUsername(requesterUsername)
    }
}