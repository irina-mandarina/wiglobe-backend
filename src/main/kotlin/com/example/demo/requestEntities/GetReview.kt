package com.example.demo.requestEntities

import com.example.demo.entities.Destination
import com.example.demo.entities.Review
import com.example.demo.entities.User
import java.sql.Timestamp
import javax.persistence.*

class GetReview(review: Review) {
    var id: Long? = null
    var user: User? = User()
    var destination: Destination? = Destination()
    var createdDate: Timestamp? = TODO()
    var starRating: Int = TODO()
    var title: String = ""
    var content: String = ""
}