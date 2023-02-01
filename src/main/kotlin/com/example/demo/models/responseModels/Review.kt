package com.example.demo.models.responseModels

import com.example.demo.serialization.TimestampSerializer
import kotlinx.serialization.Serializable
import java.sql.Timestamp

@Serializable
data class Review(val id: Long,
                  val destination: Destination,
                  val userNames: UserNames,
                  var starRating: Int,
                  @Serializable(TimestampSerializer::class)
                          val postedOn: Timestamp,
                  val title: String,
                  val content: String
)
