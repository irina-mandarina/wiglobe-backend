package com.example.demo.services.serviceImplementations

import com.example.demo.entities.FollowRequest
import com.example.demo.repositories.FollowRequestRepository
import com.example.demo.services.FollowRequestService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class FollowRequestServiceImpl(val followRequestRepository: FollowRequestRepository): FollowRequestService {
    override fun sendFollowRequest(username: String, userId: Long): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun deleteFollowRequest(username: String, userId: Long): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun getFollowRequests(username: String): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun approveFollowRequest(username: String, userId: Long, response: Boolean): ResponseEntity<String> {
        TODO("Not yet implemented")
    }
}