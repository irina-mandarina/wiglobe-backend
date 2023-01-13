package com.example.demo.models.responseModels

import kotlinx.serialization.Serializable

@Serializable
data class UserNames(val username: String,
                     val firstName: String,
                     val lastName: String?
                     )
