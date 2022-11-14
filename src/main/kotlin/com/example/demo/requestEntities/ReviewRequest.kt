package com.example.demo.requestEntities

import com.example.demo.entities.User
import java.sql.Timestamp

class ReviewRequest {
    var user: User = User()

    var createdDate: Timestamp = TODO()

    var starRating: Int = TODO()

    var title: String = ""

    var content: String = ""
}