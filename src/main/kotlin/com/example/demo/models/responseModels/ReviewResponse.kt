package com.example.demo.models.responseModels

import com.example.demo.entities.Review
import com.example.demo.serialization.TimestampSerializer
import kotlinx.serialization.Serializable
import java.sql.Timestamp


@Serializable
data class ReviewResponse(val id: Long,
                          val destination: DestinationResponse,
                          val userNames: UserNamesResponse,
                          var starRating: Int,
                          val title: String,
                          @Serializable(TimestampSerializer::class)
                          val reviewedDate: Timestamp,
                          val content: String
                          )
