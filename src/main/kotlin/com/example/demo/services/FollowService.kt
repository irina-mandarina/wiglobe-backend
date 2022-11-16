package com.example.demo.services

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader

interface FollowService {
    fun getFollowStatus(@RequestHeader username: String, @PathVariable userId: Long) : ResponseEntity<String>
    fun endFollow(@RequestHeader username: String, @PathVariable userId: Long) : ResponseEntity<String>
}