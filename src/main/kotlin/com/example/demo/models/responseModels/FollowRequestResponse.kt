package com.example.demo.models.responseModels

import com.example.demo.serialization.TimestampSerializer
import kotlinx.serialization.Serializable
import java.sql.Timestamp

@Serializable
data class FollowRequestResponse(val requester: UserNamesResponse,
                                 val receiver: UserNamesResponse,
                                 @Serializable(TimestampSerializer::class)
                                 val requestDate: Timestamp
                                 )