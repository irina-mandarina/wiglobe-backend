package com.example.demo.models.responseModels

import java.sql.Timestamp

data class Comment(val id: Long,
                   val userNames: UserNames,
                   val postedOn: Timestamp,
                   val content: String
                   )