package com.example.demo.models.responseModels

import com.example.demo.entities.FollowRequest
import kotlinx.serialization.Serializable
import java.sql.Timestamp
import java.time.LocalDateTime

@Serializable
data class FollowRequestResponse(val followRequest: FollowRequest) {
    val requester = followRequest.requester.username
    val receiver = followRequest.receiver.username
    val requestDate = followRequest.requestDate.toString()

}