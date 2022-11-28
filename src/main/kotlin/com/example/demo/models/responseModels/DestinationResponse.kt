package com.example.demo.models.responseModels

import com.example.demo.entities.Destination
import kotlinx.serialization.Serializable

@Serializable
data class DestinationResponse(val destination: Destination) {
    var x: Double = destination.x
    var y: Double = destination.y
    var name: String? = destination.name

}