package com.example.demo.services

import com.example.demo.entities.FollowRequest
import org.springframework.http.ResponseEntity

interface FollowRequestService {
    fun sendFollowRequest(username: String, userId: Long): ResponseEntity<String>
    fun deleteFollowRequest(username: String, userId: Long): ResponseEntity<String>
    fun getFollowRequests(username: String): ResponseEntity<String>
    fun approveFollowRequest(username: String, userId: Long, response: Boolean): ResponseEntity<String>
    fun findByReceiver_IdAndAndRequester_Username(receiverId: Long, requesterUsername: String): FollowRequest?
    fun findByReceiver_Username(receiverUsername: String): List<FollowRequest>
}