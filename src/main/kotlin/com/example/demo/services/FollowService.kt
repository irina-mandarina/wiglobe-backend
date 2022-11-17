package com.example.demo.services

import com.example.demo.entities.Follow
import com.example.demo.entities.FollowRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader

interface FollowService {
    //fun getFollowStatus(@RequestHeader username: String, @PathVariable userId: Long) : ResponseEntity<String>
    fun endFollow(@RequestHeader username: String, @PathVariable userId: Long) : ResponseEntity<String>
    fun saveFollow(followRequest: FollowRequest): ResponseEntity<String>
    fun findByFollower_IdAndAndFollowed_Id(followerId: Long, followedId: Long): Follow?
}