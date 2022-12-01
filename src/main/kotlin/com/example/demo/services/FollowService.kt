package com.example.demo.services

import com.example.demo.entities.Follow
import com.example.demo.entities.FollowRequest
import com.example.demo.repositories.FollowRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class FollowService(private val followRepository: FollowRepository, private val userService: UserService ) {
    fun endFollow(username: String, userId: Long): ResponseEntity<String> {
        if (userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        val follow = findByFollowerIdAndAndFollowedId(userService.findUserByUsername(username)!!.id, userId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("$username is not following them")

        followRepository.delete(follow)
        return ResponseEntity.ok().body("$username stopped following them")
    }

    fun saveFollow(followRequest: FollowRequest): Follow? {
        if (findByFollowerIdAndAndFollowedId(followRequest.requester.id, followRequest.receiver.id) != null) {
            return null
        }
        val follow = Follow(followRequest)
        return followRepository.save(follow)
    }

    fun findByFollowerIdAndAndFollowedId(followerId: Long, followedId: Long): Follow? {
        return followRepository.findByFollowerIdAndAndFollowedId(followerId, followedId)
    }

    fun areFriends(username1: String, username2: String): Boolean {
        val user1 = userService.findUserByUsername(username1)!!
        val user2 = userService.findUserByUsername(username2)!!
        if (findByFollowerIdAndAndFollowedId(user1.id, user2.id) != null && findByFollowerIdAndAndFollowedId(user2.id, user1.id) != null) {
            return true;
        }
        return false;
    }
}