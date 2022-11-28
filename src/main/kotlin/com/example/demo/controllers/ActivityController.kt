package com.example.demo.controllers

import com.example.demo.models.requestModels.PostActivity
import com.example.demo.services.ActivityService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class ActivityController(val activityService: ActivityService) {
    @PostMapping("/journeys/")
    fun addActivityToJourney(@RequestHeader username: String, @RequestBody postActivity: PostActivity,
                             @PathVariable journeyId: Long): ResponseEntity<String> {
        return activityService.addActivityToJourney(username, postActivity, journeyId)
    }
}