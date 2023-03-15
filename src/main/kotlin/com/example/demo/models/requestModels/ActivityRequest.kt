package com.example.demo.models.requestModels

import com.example.demo.types.ActivityType
import java.sql.Timestamp

data class ActivityRequest(val type: ActivityType?, val description: String?,
                           val date: Timestamp?, val image: String?) {
}