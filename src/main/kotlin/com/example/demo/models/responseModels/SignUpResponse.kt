package com.example.demo.models.responseModels


data class SignUpResponse(val emailTaken: Boolean, val usernameTaken: Boolean, val createdUserDetails: Boolean, val userDetails: UserDetails)
