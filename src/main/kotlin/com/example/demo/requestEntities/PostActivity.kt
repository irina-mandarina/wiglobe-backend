package com.example.demo.requestEntities

import com.example.demo.types.ActivityTypes

data class PostActivity(val type: ActivityTypes, val description: String,
                        val location: String, val title: String,
                        val activityType: ActivityTypes = ActivityTypes.OTHER) {
}