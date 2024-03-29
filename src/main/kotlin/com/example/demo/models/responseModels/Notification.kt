package com.example.demo.models.responseModels

import com.example.demo.types.NotificationObjectEntityType
import java.sql.Timestamp

data class Notification(val receiver: String, val subject: String, val timestamp: Timestamp,
                        val objectType: NotificationObjectEntityType?, val objectId: Long?,
                        val prepositionObjectType: NotificationObjectEntityType?, val prepositionObjectId: Long?,
                        val content: String)
