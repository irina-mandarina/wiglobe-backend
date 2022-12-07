package com.example.demo.models.responseModels

import com.example.demo.serialization.TimestampSerializer
import kotlinx.serialization.Serializable
import java.sql.Timestamp

@Serializable
data class FollowRequest(val requester: UserNames,
                         val receiver: UserNames,
                         @Serializable(TimestampSerializer::class)
                                 val requestDate: Timestamp
                                 )