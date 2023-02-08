package com.example.demo.models.responseModels

data class Country(val countryCode: String,
                   val countryName: String,
                   val capital: String?,
                   val area: String?,
                   val continent: String?
)
