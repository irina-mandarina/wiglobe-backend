package com.example.demo.requestEntities

import com.example.demo.entities.Comment
data class GetComment(val comment: Comment) {
        val id = comment.id
        val userNames = GetUserNames(comment.user.username,
            comment.user.firstName, comment.user.lastName)
        val datePosted = comment.datePosted
        val content = comment.content
}