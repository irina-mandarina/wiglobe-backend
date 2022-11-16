package com.example.demo.services.serviceImplementations

import com.example.demo.services.FollowService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class FollowServiceImpl: FollowService {
    override fun getFollowStatus(username: String, userId: Long): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun endFollow(username: String, userId: Long): ResponseEntity<String> {
        TODO("Not yet implemented")
    }
}