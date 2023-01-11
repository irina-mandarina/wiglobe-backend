package com.example.demo.services

import com.example.demo.entities.ActivityEntity
import com.example.demo.entities.JourneyEntity
import com.example.demo.repositories.ActivityRepository
import com.example.demo.models.requestModels.ActivityRequest
import com.example.demo.models.responseModels.Activity
import com.example.demo.types.ActivityTypes
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ActivityService(
    private val activityRepository: ActivityRepository, private val journeyService: JourneyService,
    private val userService: UserService) {

    fun activityFromEntity(activityEntity: ActivityEntity): Activity {
        return Activity(
            activityEntity.id!!,
            activityEntity.title,
            activityEntity.description,
            activityEntity.type,
            activityEntity.date,
            activityEntity.location
        )
    }
    fun addActivityToJourney(username: String, postActivity: ActivityRequest,
                             journeyId: Long): ResponseEntity<String> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(
                "message","Username does not exist")
                .body(null)
        }

        if (!journeyService.journeyWithIdExists(journeyId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message","Journey does not exist")
                .body(null)
        }

        val journey = journeyService.findJourneyById(journeyId)!!

        val activity = ActivityEntity(postActivity, journey)
        activityRepository.save(activity)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            Json.encodeToString(
                activityFromEntity(activity)
            )
        )
    }

    fun editActivityForJourney(username: String, activityRequest: ActivityRequest, journeyId: Long,
                               activityId: Long): ResponseEntity<Activity> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(
                "message","Username does not exist")
                .body(null)
        }

        if (!journeyService.journeyWithIdExists(journeyId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message","Journey does not exist")
                .body(null)
        }

        if (!activityWithIdExists(activityId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message","Activity does not exist")
                .body(null)
        }

        val journey = journeyService.findJourneyById(journeyId)!!
        if (journey.id != journeyId) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message","Activity does not belong to journey")
                .body(null)
        }

        val activity = findActivityById(activityId)!!
        activity.description = activityRequest.description
        activity.location = activityRequest.location
        activity.title = activityRequest.title
        activity.type = activityRequest.type
        activity.date = activityRequest.date

        activityRepository.save(activity)

        return ResponseEntity.status(HttpStatus.OK).body(
            activityFromEntity(activity)
        )
    }

    fun deleteActivityFromJourney(username: String, journeyId: Long, activityId: Long): ResponseEntity<String> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(
                "message","Username does not exist")
                .body(null)
        }

        if (!journeyService.journeyWithIdExists(journeyId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message","Journey does not exist")
                .body(null)
        }

        if (!activityWithIdExists(activityId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message","Activity does not exist")
                .body(null)
        }

        val activity = findActivityById(activityId)!!
        activityRepository.delete(activity)

        return ResponseEntity.status(HttpStatus.OK).header(
                "message",
            "Successfully deleted an activity"
        )
        .body(null)
    }

    fun activityWithIdExists(id: Long): Boolean {
        return (activityRepository.findActivityById(id) != null)
    }

    fun findActivityById(id: Long): ActivityEntity? {
        return activityRepository.findActivityById(id)
    }

    fun findActivitiesByJourney(journey: JourneyEntity): List<ActivityEntity> {
        return activityRepository.findActivitiesByJourney(journey)
    }

    fun getActivitiesForJourney(username: String, journeyId: Long): ResponseEntity<List<Activity>> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
        }

        if (!journeyService.journeyWithIdExists(journeyId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }

        val activities = findActivitiesByJourney(journeyService.findJourneyById(journeyId)!!).map {
            activityFromEntity(it)
        }

        return ResponseEntity.status(HttpStatus.OK).body(
            activities
        )
    }

    fun getAllActivityTypes(): ResponseEntity<List<ActivityTypes>> {
        return ResponseEntity.ok().body(
            ActivityTypes.values().toList()
        )
    }
}