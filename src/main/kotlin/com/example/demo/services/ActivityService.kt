package com.example.demo.services

import com.example.demo.entities.ActivityEntity
import com.example.demo.entities.JourneyEntity
import com.example.demo.repositories.ActivityRepository
import com.example.demo.models.requestModels.ActivityRequest
import com.example.demo.models.responseModels.Activity
import com.example.demo.types.ActivityType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ActivityService(private val activityRepository: ActivityRepository,
                      private val journeyService: JourneyService) {

    private fun activityFromEntity(activityEntity: ActivityEntity): Activity {
        return Activity(
            activityEntity.id,
            activityEntity.description,
            activityEntity.type,
            activityEntity.date,
            activityEntity.image
        )
    }

    fun addActivityToJourney(activityRequest: ActivityRequest, journeyId: Long): ResponseEntity<Activity> {
        if (!journeyService.journeyWithIdExists(journeyId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message","Journey does not exist")
                .body(null)
        }

        val journey = journeyService.findJourneyById(journeyId)!!

        val activity = ActivityEntity(activityRequest, journey)
        activityRepository.save(activity)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            activityFromEntity(activity)
        )
    }

    fun editActivityForJourney(username: String, activityRequest: ActivityRequest, journeyId: Long,
                               activityId: Long): ResponseEntity<Activity> {
        if (!journeyService.journeyWithIdExists(journeyId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null)
        }

        if (!activityWithIdExists(activityId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null)
        }

        val journey = journeyService.findJourneyById(journeyId)!!
        if (journey.id != journeyId) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null)
        }

        val activity = findActivityById(activityId)!!
        activity.description = activityRequest.description.toString()
        activity.type = activityRequest.type!!
        activity.date = activityRequest.date!!

        activityRepository.save(activity)

        return ResponseEntity.status(HttpStatus.OK).body(
            activityFromEntity(activity)
        )
    }

    fun deleteActivityFromJourney(username: String, journeyId: Long, activityId: Long): ResponseEntity<Void> {
        if (!journeyService.journeyWithIdExists(journeyId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null)
        }

        if (!activityWithIdExists(activityId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null)
        }

        val activity = findActivityById(activityId)!!
        activityRepository.delete(activity)

        return ResponseEntity.status(HttpStatus.OK).body(null)
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

    fun getActivitiesForJourney(journeyId: Long): ResponseEntity<List<Activity>> {
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

    fun getAllActivityTypes(): ResponseEntity<List<ActivityType>> {
        return ResponseEntity.ok().body(
            ActivityType.values().toList()
        )
    }
}