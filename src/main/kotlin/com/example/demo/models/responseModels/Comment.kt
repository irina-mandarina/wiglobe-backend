package com.example.demo.models.responseModels

import com.example.demo.serialization.TimestampSerializer
import kotlinx.serialization.Serializable
import java.sql.Timestamp

@Serializable
data class Comment(val id: Long,
                   val userNames: UserNames,
                   @Serializable(TimestampSerializer::class)
                           val datePosted: Timestamp,
                   val content: String
                           )