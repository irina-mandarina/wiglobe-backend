package com.example.demo.models.requestModels

data class GooglePayload(val email: String,
                         val email_verified: Boolean,
                         val family_name: String?,
                         val given_name: String?,
                         val hd: String?,
                         val id: String?,
                         val name: String?,
                         val picture: String?)