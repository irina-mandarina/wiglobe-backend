package com.example.demo.models.responseModels

import java.sql.Timestamp

data class Follow(val follower: UserNames,
                  val followed: UserNames,
                  val followDate: Timestamp
)
