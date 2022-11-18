package com.example.demo.services.serviceImplementations

import com.example.demo.entities.FollowRequest
import com.example.demo.repositories.FollowRequestRepository
import com.example.demo.requestEntities.GetFollowRequest
import com.example.demo.services.FollowRequestService
import com.example.demo.services.FollowService
import com.example.demo.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.LocalDateTime

@Service
class FollowRequestServiceImpl(val followRequestRepository: FollowRequestRepository, val followService: FollowService,
                               val userService: UserService): FollowRequestService {
    override fun sendFollowRequest(username: String, userId: Long): ResponseEntity<String> {
        if (userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        if (findByReceiver_IdAndAndRequester_Username(userId, username) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("$username already requested to follow that user")
        }

        val receiver = userService.findUserById(userId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id: $userId does not exist")
        val followRequest = FollowRequest()

        followRequest.receiver = receiver
        followRequest.requester = userService.findUserByUsername(username)!!
        followRequest.requestDate = Timestamp.valueOf(LocalDateTime.now())

        followRequestRepository.save(followRequest)

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("$username requested to follow ${receiver.username}")
    }

    override fun deleteFollowRequest(username: String, userId: Long): ResponseEntity<String> {
        if (userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        val request = findByReceiver_IdAndAndRequester_Username(userId, username)
            ?: return ResponseEntity.status(HttpStatus.CREATED).body("$username has not requested to follow that user")

        followRequestRepository.delete(request)
        return ResponseEntity.ok().body("Deleted $username's request to follow that user")
    }

    override fun getFollowRequests(username: String): ResponseEntity<String> {
        if (userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        val requests = findByReceiver_Username(username)
        return ResponseEntity.ok().body(requests.map{ GetFollowRequest(it) } .toString())
    }

    override fun approveFollowRequest(username: String, userId: Long, response: Boolean): ResponseEntity<String> {
        if (userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        val request = findByReceiver_IdAndAndRequester_Username(userId, username)
            ?: return ResponseEntity.status(HttpStatus.CREATED).body("$username has not requested to follow that user")

        if (response) {
            followService.saveFollow(request)

        }

        followRequestRepository.delete(request)
        return ResponseEntity.ok().body("Deleted $username's request to follow that user")
    }

    override fun findByReceiver_IdAndAndRequester_Username(receiverId: Long, requesterUsername: String): FollowRequest? {
        return followRequestRepository.findByReceiver_IdAndAndRequester_Username(receiverId, requesterUsername)
    }

    override fun findByReceiver_Username(receiverUsername: String): List<FollowRequest> {
        return followRequestRepository.findByReceiver_Username(receiverUsername)
    }
}