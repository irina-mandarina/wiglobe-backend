package com.example.demo.services

import org.springframework.http.ResponseEntity

interface FriendRequestService {
    abstract fun sendFriendRequest(username: String, userId: Long): ResponseEntity<String>

    abstract fun deleteFriendRequest(username: String, userId: Long): ResponseEntity<String>

    abstract fun getFriendRequests(username: String): ResponseEntity<String>

    abstract fun respondToFriendRequest(username: String, userId: Long, response: Boolean): ResponseEntity<String>
}