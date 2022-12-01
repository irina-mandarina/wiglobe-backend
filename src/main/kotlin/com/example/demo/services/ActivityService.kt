package com.example.demo.services

import com.example.demo.entities.Activity
import com.example.demo.entities.Journey
import com.example.demo.repositories.ActivityRepository
import com.example.demo.models.requestModels.ActivityRequest
import com.example.demo.models.responseModels.ActivityResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ActivityService(
    private val activityRepository: ActivityRepository, private val journeyService: JourneyService,
    private val userService: UserService) {
    fun addActivityToJourney(username: String, postActivity: ActivityRequest,
                             journeyId: Long): ResponseEntity<String> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        if (!journeyService.journeyWithIdExists(journeyId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journey does not exist")
        }

        val journey = journeyService.findJourneyById(journeyId)!!

        val activity = Activity(postActivity, journey)
        activityRepository.save(activity)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            Json.encodeToString(
                ActivityResponse(
                    activity.id!!,
                    activity.title,
                    activity.description,
                    activity.type,
                    activity.date,
                    activity.location
                )
            )
        )
    }

    fun editActivityForJourney(username: String, activityRequest: ActivityRequest, journeyId: Long, activityId: Long): ResponseEntity<String> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        if (!journeyService.journeyWithIdExists(journeyId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journey does not exist")
        }

        if (!activityWithIdExists(activityId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Activity does not exist")
        }

        val journey = journeyService.findJourneyById(journeyId)!!
        if (journey.id != journeyId) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Activity does not belong to journey")
        }

        val activity = findActivityById(activityId)!!
        activity.description = activityRequest.description
        activity.location = activityRequest.location
        activity.title = activityRequest.title
        activity.type = activityRequest.type
        activity.date = activityRequest.date

        activityRepository.save(activity)

        return ResponseEntity.status(HttpStatus.OK).body(
            Json.encodeToString(
                ActivityResponse(
                    activity.id!!,
                    activity.title,
                    activity.description,
                    activity.type,
                    activity.date,
                    activity.location
                )
            )
        )
    }

    fun deleteActivityFromJourney(username: String, journeyId: Long, activityId: Long): ResponseEntity<String> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        if (!journeyService.journeyWithIdExists(journeyId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journey does not exist")
        }

        if (!activityWithIdExists(activityId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Activity does not exist")
        }

        val activity = findActivityById(activityId)!!
        activityRepository.delete(activity)

        return ResponseEntity.status(HttpStatus.OK).body(
            "Successfully deleted an activity"
        )
    }

    fun activityWithIdExists(id: Long): Boolean {
        return (activityRepository.findActivityById(id) != null)
    }

    fun findActivityById(id: Long): Activity? {
        return activityRepository.findActivityById(id)
    }

    fun findActivitiesByJourney(journey: Journey): List<Activity> {
        return activityRepository.findActivitiesByJourney(journey)
    }

    fun getActivitiesForJourney(username: String, journeyId: Long): ResponseEntity<String> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist")
        }

        if (!journeyService.journeyWithIdExists(journeyId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journey does not exist")
        }

        val activities = findActivitiesByJourney(journeyService.findJourneyById(journeyId)!!).map {
            ActivityResponse(
                it.id!!,
                it.title,
                it.description,
                it.type,
                it.date,
                it.location
            )
        }

        return ResponseEntity.status(HttpStatus.OK).body(
            Json.encodeToString(
                activities
            )
        )
    }
}