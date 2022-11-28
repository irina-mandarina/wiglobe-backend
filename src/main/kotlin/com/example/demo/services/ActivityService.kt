package com.example.demo.services

import com.example.demo.entities.Activity
import com.example.demo.repositories.ActivityRepository
import com.example.demo.models.requestModels.PostActivity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ActivityService(val activityRepository: ActivityRepository, val journeyService: JourneyService,
                      val userService: UserService) {
    fun addActivityToJourney(username: String, postActivity: PostActivity,
                             journeyId: Long): ResponseEntity<String> {
        if (userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        if (journeyService.journeyWithIdExists(journeyId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journey does not exist")
        }

        val activity = Activity(postActivity, journeyId)
        activityRepository.save(activity)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            "JourneyResponse(journeyService.findJourneyById(journeyId)!!) .toString()"
        )
    }
}