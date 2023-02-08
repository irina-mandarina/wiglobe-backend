package com.example.demo.models.responseModels

import java.sql.Timestamp

data class FollowRequest(val requester: UserNames,
                         val receiver: UserNames,
                         val requestDate: Timestamp
                         )