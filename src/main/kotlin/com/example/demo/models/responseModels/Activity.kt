package com.example.demo.models.responseModels

import com.example.demo.types.ActivityType
import java.sql.Timestamp

data class Activity(val id: Long,
                    val title: String?,
                    val description: String?,
                    val type: ActivityType?,
                    val date: Timestamp?,
                    val location: String?)
