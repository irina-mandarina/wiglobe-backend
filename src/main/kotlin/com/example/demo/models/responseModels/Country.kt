package com.example.demo.models.responseModels

import kotlinx.serialization.Serializable

@Serializable
data class Country(val countryCode: String,
                   val countryName: String,
                   val capital: String,
                   val area: String,
                   val continent: String
)
