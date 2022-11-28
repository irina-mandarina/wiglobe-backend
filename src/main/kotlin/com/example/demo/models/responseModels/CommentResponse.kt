package com.example.demo.models.responseModels

import com.example.demo.entities.Comment
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class CommentResponse(val comment: Comment) {
        val id = comment.id
        val userNames = UserNamesResponse(comment.user.username,
            comment.user.firstName, comment.user.lastName)
        val datePosted = comment.datePosted.toString()
        val content = comment.content
}