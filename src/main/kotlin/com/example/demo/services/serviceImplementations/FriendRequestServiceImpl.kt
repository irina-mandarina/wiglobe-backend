package com.example.demo.services.serviceImplementations

import com.example.demo.entities.FriendRequest
import com.example.demo.repositories.FriendRequestRepository
import com.example.demo.services.FriendRequestService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class FriendRequestServiceImpl: FriendRequestService {
    private val friendRequestRepository: FriendRequestRepository = TODO()

    override fun sendFriendRequest(username: String, userId: Long): ResponseEntity<String> {
        var newFriendRequest: FriendRequest
        // setters
        friendRequestRepository.save(newFriendRequest)

        return ResponseEntity(
            HttpStatus.OK
        )
    }

    override fun deleteFriendRequest(username: String, userId: Long): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun getFriendRequests(username: String): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun respondToFriendRequest(username: String, userId: Long, response: Boolean): ResponseEntity<String> {
        TODO("Not yet implemented")
    }
}