package com.example.demo.requestEntities

import com.example.demo.entities.FollowRequest
import java.sql.Timestamp
import java.time.LocalDateTime

class GetFollowRequest {
    var requester: String = ""
    var receiver: String = ""
    var requestDate: Timestamp = Timestamp.valueOf(LocalDateTime.now())

    constructor(followRequest: FollowRequest) {
        this.requester = followRequest.requester.username
        this.receiver = followRequest.receiver.username
        this.requestDate = followRequest.requestDate
    }
}