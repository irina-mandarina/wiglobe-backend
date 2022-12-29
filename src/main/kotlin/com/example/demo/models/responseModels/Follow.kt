package com.example.demo.models.responseModels

import com.example.demo.serialization.TimestampSerializer
import kotlinx.serialization.Serializable
import java.sql.Timestamp

@Serializable
data class Follow(val follower: UserNames,
                  val followed: UserNames,
                  @Serializable(TimestampSerializer::class)
                          val followDate: Timestamp
)
