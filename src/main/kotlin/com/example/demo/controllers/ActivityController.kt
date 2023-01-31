package com.example.demo.controllers

import com.example.demo.models.requestModels.ActivityRequest
import com.example.demo.models.responseModels.Activity
import com.example.demo.services.ActivityService
import com.example.demo.types.ActivityType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("http://localhost:3000")
class ActivityController(private val activityService: ActivityService) {
    @PostMapping("/journeys/{journeyId}/activities")
    fun addActivityToJourney(@RequestAttribute username: String, @RequestBody activityRequest: ActivityRequest,
                             @PathVariable journeyId: Long): ResponseEntity<Activity> {
        return activityService.addActivityToJourney(username, activityRequest, journeyId)
    }

    @PutMapping("/journeys/{journeyId}/activities/{activityId}")
    fun editActivityForJourney(@RequestAttribute username: String, @RequestBody activityRequest: ActivityRequest,
                               @PathVariable journeyId: Long, @PathVariable activityId: Long): ResponseEntity<Activity> {
        return activityService.editActivityForJourney(username, activityRequest, journeyId, activityId)
    }

    @DeleteMapping("/journeys/{journeyId}/activities/{activityId}")
    fun deleteActivityFromJourney(@RequestAttribute username: String,
                               @PathVariable journeyId: Long, @PathVariable activityId: Long): ResponseEntity<String> {
        return activityService.deleteActivityFromJourney(username, journeyId, activityId)
    }

    @GetMapping("/journeys/{journeyId}/activities")
    fun getActivitiesForJourney(@RequestAttribute username: String,
                                @PathVariable journeyId: Long): ResponseEntity<List<Activity>> {
        return activityService.getActivitiesForJourney(username, journeyId)
    }

    @GetMapping("/activities")
    fun getAllActivityTypes(): ResponseEntity<List<ActivityType>> {
        return activityService.getAllActivityTypes();
    }

}