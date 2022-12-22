package com.example.demo.services

import com.example.demo.entities.FollowEntity
import com.example.demo.entities.FollowRequestEntity
import com.example.demo.models.responseModels.Follow
import com.example.demo.models.responseModels.UserDetails
import com.example.demo.repositories.FollowRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class FollowService(private val followRepository: FollowRepository, private val userService: UserService ) {
    fun followFromEntity(followEntity: FollowEntity): Follow {
        return Follow (
            userService.userNames(followEntity.followed),
            userService.userNames(followEntity.follower),
            followEntity.followDate
        )
    }

    fun endFollow(username: String, userId: Long): ResponseEntity<String> {
        if (userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(
                "message", "Username does not exist").body(null)
        }

        val follow = findByFollowerIdAndAndFollowedId(userService.findUserByUsername(username)!!.id, userId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message", "$username is not following them").body(null)

        followRepository.delete(follow)
        return ResponseEntity.ok().header(
            "message", "$username stopped following them").body(null)
    }

    fun saveFollow(followRequest: FollowRequestEntity): FollowEntity? {
        if (findByFollowerIdAndAndFollowedId(followRequest.requester.id, followRequest.receiver.id) != null) {
            return null
        }
        val follow = FollowEntity(followRequest)
        return followRepository.save(follow)
    }

    fun findByFollowerIdAndAndFollowedId(followerId: Long, followedId: Long): FollowEntity? {
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

    fun getFollowers(username: String): ResponseEntity<List<UserDetails>> {
        return ResponseEntity.ok().body(
            findAllByFollowedUsernameOrderByFollowDate(username).map {
                userService.userDetails(
                    it.follower
                )
            }
        )
    }

    fun getFollowing(username: String): ResponseEntity<List<UserDetails>> {
        return ResponseEntity.ok().body(
            findAllByFollowerUsernameOrderByFollowDate(username).map {
                userService.userDetails(
                    it.followed
                )
            }
        )
    }

    fun getFriends(username: String): ResponseEntity<List<UserDetails>> {
        return ResponseEntity.ok().body(
            findAllByFollowerUsernameOrderByFollowDate(username)
                .filter {
                    areFriends(it.followed.username, it.follower.username)
                }
                .map {
                    userService.userDetails(
                        it.followed
                    )
                }
        )
    }

    fun findAllByFollowedUsernameOrderByFollowDate(username: String): List<FollowEntity> {
        return followRepository.findAllByFollowedUsernameOrderByFollowDate(username)
    }

    fun findAllByFollowerUsernameOrderByFollowDate(username: String): List<FollowEntity> {
        return followRepository.findAllByFollowerUsernameOrderByFollowDate(username)
    }
}