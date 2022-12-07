package com.example.demo.models.requestModels

import com.example.demo.types.ActivityTypes
import java.sql.Timestamp

data class ActivityRequest(val type: ActivityTypes, val description: String,
                           val location: String, val title: String,
                           val date: Timestamp) {
}