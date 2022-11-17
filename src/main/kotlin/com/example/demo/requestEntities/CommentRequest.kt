package com.example.demo.requestEntities

import com.example.demo.entities.Comment
import java.sql.Timestamp

class CommentRequest {
    var id: Long? = null
    var journeyId: Long? = null
    var userId: Long? = null
    var datePosted: Timestamp? = TODO()
    var content: String = ""

    constructor(comment: Comment) {
        this.journeyId = comment.journey.id
        this.userId = comment.user.id
        this.datePosted = comment.datePosted
        this.content = comment.content
    }

    constructor(commentRequest: CommentRequest, journeyId: Long, userId: Long) {
        this.userId = userId
        this.journeyId = journeyId
        this.datePosted = commentRequest.datePosted
        this.content = commentRequest.content
    }
}