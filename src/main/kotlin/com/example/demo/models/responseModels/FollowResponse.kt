package com.example.demo.models.responseModels

import com.example.demo.serialization.TimestampSerializer
import kotlinx.serialization.Serializable
import java.sql.Timestamp

@Serializable
data class FollowResponse(val follower: UserNamesResponse,
                          val followed: UserNamesResponse,
                          @Serializable(TimestampSerializer::class)
                          val followDate: Timestamp
                          )
