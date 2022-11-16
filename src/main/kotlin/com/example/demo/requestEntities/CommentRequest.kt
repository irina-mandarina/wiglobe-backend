package com.example.demo.requestEntities

import com.example.demo.entities.Journey
import com.example.demo.entities.User
import java.sql.Timestamp

class CommentRequest {
    var id: Long? = null

    var journey: Journey? = Journey()

    var user: User? = User()

    var datePosted: Timestamp = TODO()

    var content: String = TODO()
}