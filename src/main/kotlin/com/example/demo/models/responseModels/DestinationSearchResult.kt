package com.example.demo.models.responseModels

data class DestinationSearchResult(val destinationId: Long,
                                   val featureCode: String?,
                                   val name: String,
                                   val country: String?)
