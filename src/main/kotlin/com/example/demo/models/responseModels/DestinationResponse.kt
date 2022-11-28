package com.example.demo.models.responseModels

import com.example.demo.entities.Destination
import kotlinx.serialization.Serializable

@Serializable
data class DestinationResponse(val x: Double,
                               val y: Double,
                               val name: String
                               )
