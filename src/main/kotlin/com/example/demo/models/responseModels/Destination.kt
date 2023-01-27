package com.example.demo.models.responseModels

import com.example.demo.models.responseModels.Country
import kotlinx.serialization.Serializable

@Serializable
data class Destination( val id: Long,
                        val latitude: Double,
                        val longitude: Double,
                        val name: String,
                        val country: Country?,
                        val featureClass: String?,
                        val featureCode: String?,
                       )
