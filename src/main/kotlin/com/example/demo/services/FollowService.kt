package com.example.demo.services

import com.example.demo.entities.Follow
import com.example.demo.entities.FollowRequest
import com.example.demo.repositories.FollowRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class FollowService( val followRepository: FollowRepository, val userService: UserService ) {
    fun endFollow(username: String, userId: Long): ResponseEntity<String> {
        if (userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        val follow = findByFollower_IdAndAndFollowed_Id(userService.findUserByUsername(username)!!.id, userId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("$username is not following them")

        followRepository.delete(follow)
        return ResponseEntity.ok().body("$username stopped following them")
    }

    fun saveFollow(followRequest: FollowRequest): Follow? {
        if (findByFollower_IdAndAndFollowed_Id(followRequest.requester.id, followRequest.receiver.id) != null) {
            return null
        }
        val follow = Follow(followRequest)
        return followRepository.save(follow)
    }

    fun findByFollower_IdAndAndFollowed_Id(followerId: Long, followedId: Long): Follow? {
        return followRepository.findByFollower_IdAndAndFollowed_Id(followerId, followedId)
    }
}