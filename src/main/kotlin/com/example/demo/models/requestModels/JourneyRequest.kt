package com.example.demo.models.requestModels

import com.example.demo.types.Visibility
import java.sql.Timestamp

data class JourneyRequest(val startDate: Timestamp?, val endDate: Timestamp?,
                          val destinationId: Long?, val description: String?,
                          val visibility: Visibility?)