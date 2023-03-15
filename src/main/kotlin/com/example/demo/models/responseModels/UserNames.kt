package com.example.demo.models.responseModels

import com.example.demo.types.Gender

data class UserNames(val username: String,
                     val firstName: String,
                     val lastName: String?,
                     val profilePicture: String?,
                     val gender: Gender)
