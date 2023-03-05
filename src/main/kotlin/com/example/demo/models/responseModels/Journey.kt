package com.example.demo.models.responseModels

import com.example.demo.types.Visibility
import java.sql.Timestamp

data class Journey(val id: Long,
                   val usernames: UserNames,
                   val postedOn: Timestamp,
                   val startDate: Timestamp?,
                   val endDate: Timestamp?,
                   val description: String?,
                   val destination: Destination?,
                   val activities: List<Activity>,
                   val visibility: Visibility,
                   val images: List<String>
)