package com.example.demo.models.requestModels

import com.example.demo.types.Visibility
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.springframework.web.multipart.MultipartFile
import java.sql.Timestamp

data class JourneyRequest(val id: Long?,
                          val startDate: Timestamp?,
                          val endDate: Timestamp?,
                          val destinationId: Long?,
                          val description: String?,
                          val visibility: Visibility?,
//                          val images: List<MultipartFile>?
)