package com.example.demo.services.serviceImplementations

import com.example.demo.services.FriendshipService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class FriendshipServiceImpl: FriendshipService {
    override fun getFriendshipStatus(username: String, userId: Long): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun endFriendship(username: String, userId: Long): ResponseEntity<String> {
        TODO("Not yet implemented")
    }
}