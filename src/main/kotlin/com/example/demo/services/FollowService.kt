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

    fun unfollow(username: String, usernameBeingFollowed: String): ResponseEntity<String> {
        if (!userService.userWithUsernameExists(username) || !userService.userWithUsernameExists(usernameBeingFollowed)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(
                "message", "Username does not exist").body(null)
        }

        val follow = findByFollowerUsernameAndFollowedUsername(username, usernameBeingFollowed)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message", "$username is not following them").body(null)

        followRepository.delete(follow)
        return ResponseEntity.ok().header(
            "message", "$username stopped following them").body(null)
    }

    fun saveFollow(followRequest: FollowRequestEntity): FollowEntity? {
        if (findByFollowerUsernameAndFollowedUsername(followRequest.requester.username, followRequest.receiver.username) != null) {
            return null
        }
        val follow = FollowEntity(followRequest)
        return followRepository.save(follow)
    }

    fun findByFollowerUsernameAndFollowedUsername(follower: String, followed: String): FollowEntity? {
        return followRepository.findByFollowerUsernameAndFollowedUsername(follower, followed)
    }

    fun areFriends(username1: String, username2: String): Boolean {
        if (findByFollowerUsernameAndFollowedUsername(username1, username2) != null && findByFollowerUsernameAndFollowedUsername(username2, username1) != null) {
            return true
        }
        return false
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
                    // for every person that USERNAME is following,
                    // check if they are following USERNAME
                    isFollowing(it.followed.username, username)
                }
                .map {
                    userService.userDetails(
                        it.followed
                    )
                }
        )
    }

    fun isFollowing(follower: String, followed: String): Boolean {
        return followRepository.findByFollowerUsernameAndFollowedUsername(follower, followed) != null

    }

    fun findAllByFollowedUsernameOrderByFollowDate(username: String): List<FollowEntity> {
        return followRepository.findAllByFollowedUsernameOrderByFollowDate(username)
    }

    fun findAllByFollowerUsernameOrderByFollowDate(username: String): List<FollowEntity> {
        return followRepository.findAllByFollowerUsernameOrderByFollowDate(username)
    }
}