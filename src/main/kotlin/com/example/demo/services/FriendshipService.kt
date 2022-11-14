package com.example.demo.services

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader

interface FriendshipService {
    fun getFriendshipStatus(@RequestHeader username: String, @PathVariable userId: Long) : ResponseEntity<String>

    fun endFriendship(@RequestHeader username: String, @PathVariable userId: Long) : ResponseEntity<String>
}