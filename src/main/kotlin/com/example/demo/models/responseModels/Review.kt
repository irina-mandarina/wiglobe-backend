package com.example.demo.models.responseModels

import java.sql.Timestamp

data class Review(val id: Long,
                  val destination: Destination,
                  val userNames: UserNames,
                  var starRating: Int,
                  val postedOn: Timestamp,
                  val title: String,
                  val content: String
)
