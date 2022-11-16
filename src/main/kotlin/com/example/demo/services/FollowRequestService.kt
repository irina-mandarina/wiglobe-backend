package com.example.demo.services

import org.springframework.http.ResponseEntity

interface FollowRequestService {
    fun sendFollowRequest(username: String, userId: Long): ResponseEntity<String>
    fun deleteFollowRequest(username: String, userId: Long): ResponseEntity<String>
    fun getFollowRequests(username: String): ResponseEntity<String>
    fun approveFollowRequest(username: String, userId: Long, response: Boolean): ResponseEntity<String>
}