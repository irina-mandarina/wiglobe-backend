package com.example.demo.models

import com.example.demo.types.Visibility
import java.sql.Timestamp

data class PostJourney(val startDate: Timestamp, val endDate: Timestamp,
                       val destinationId: Long, val description: String,
                       val visibility: Visibility = Visibility.FRIEND_ONLY)