package com.example.demo.models.responseModels

import kotlinx.serialization.Serializable

@Serializable
data class Destination(val latitude: Double,
                       val longitude: Double,
                       val name: String
                       )
