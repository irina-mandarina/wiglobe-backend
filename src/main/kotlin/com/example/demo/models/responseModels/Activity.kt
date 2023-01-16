package com.example.demo.models.responseModels

import com.example.demo.serialization.TimestampSerializer
import com.example.demo.types.ActivityType
import kotlinx.serialization.Serializable
import java.sql.Timestamp

@Serializable
data class Activity(val id: Long,
                    val title: String?,
                    val description: String?,
                    val type: ActivityType?,
                    @Serializable(TimestampSerializer::class)
                            val date: Timestamp?,
                    val location: String?)
