package com.example.demo.models.responseModels

data class Destination( val id: Long,
                        val latitude: Double,
                        val longitude: Double,
                        val name: String,
                        val country: Country?,
                        val featureClass: String?,
                        val featureCode: String?,
                        val visitCount: Int?,
                        val averageRating: Double?,
                        val reviewCount: Int?
                       )
