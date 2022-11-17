package com.example.demo.services.serviceImplementations

import com.example.demo.entities.Follow
import com.example.demo.entities.FollowRequest
import com.example.demo.repositories.FollowRepository
import com.example.demo.services.FollowService
import com.example.demo.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.LocalDateTime

@Service
class FollowServiceImpl(val followRepository: FollowRepository, val userService: UserService): FollowService {
    override fun endFollow(username: String, userId: Long): ResponseEntity<String> {
        if (username.isBlank()) {
            return ResponseEntity.badRequest().body("Missing request parameter: username")
        }

        if (userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        if (userId == null) {
            return ResponseEntity.badRequest().body("Missing request parameter: userId")
        }

        val follow = findByFollower_IdAndAndFollowed_Id(userService.findUserByUsername(username)!!.id, userId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("$username is not following them")

        followRepository.delete(follow)
        return ResponseEntity.ok().body("$username stopped following them")
    }

    override fun saveFollow(followRequest: FollowRequest): ResponseEntity<String> {
        if (findByFollower_IdAndAndFollowed_Id(followRequest.requester.id, followRequest.receiver.id) != null) {
            return ResponseEntity.accepted().body("Already followed")
        }
        val follow = Follow(followRequest)
        followRepository.save(follow)
        return ResponseEntity.ok().body("Already followed")
    }

    override fun findByFollower_IdAndAndFollowed_Id(followerId: Long, followedId: Long): Follow? {
        return followRepository.findByFollower_IdAndAndFollowed_Id(followerId, followedId)
    }
}