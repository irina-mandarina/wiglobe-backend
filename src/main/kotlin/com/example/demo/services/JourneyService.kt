package com.example.demo.services

import com.example.demo.entities.Journey
import com.example.demo.entities.User
import com.example.demo.repositories.JourneyRepository
import com.example.demo.models.responseModels.JourneyResponse
import com.example.demo.models.requestModels.JourneyRequest
import com.example.demo.models.responseModels.ActivityResponse
import com.example.demo.models.responseModels.UserNamesResponse
import com.example.demo.types.Visibility
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service


@Service
class JourneyService(private val journeyRepository: JourneyRepository, private val userService: UserService,
                     private val destinationService: DestinationService, private val followService: FollowService) {

    fun findJourneyById(id: Long): Journey? {
        return journeyRepository.findJourneysById(id)
    }

    fun findJourneysByUser(user: User): List<Journey> {
        return journeyRepository.findJourneysByUser(user)
    }

    fun journeyWithIdExists(id: Long): Boolean {
        return findJourneyById(id) != null
    }

    fun createJourney(username: String, journeyRequest: JourneyRequest): ResponseEntity<String> {
        if (username.isBlank()) {
            return ResponseEntity.badRequest().body("Missing request parameter: username")
        }

        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.badRequest().body("Username does not exist")
        }

        val journey = Journey()
        journey.user = userService.findUserByUsername(username)!!
        journey.destination = destinationService.findDestinationById(journeyRequest.destinationId)!!
        journey.startDate = journeyRequest.startDate
        journey.endDate = journeyRequest.endDate
        journey.description = journeyRequest.description

        journeyRepository.save(journey)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            Json.encodeToString(
                JourneyResponse(
                    journey.id!!,
                    UserNamesResponse(
                        journey.user.username, journey.user.firstName, journey.user.lastName),
                    journey.startDate, journey.endDate!!,
                    journey.description, journey.destination.name,
                    journey.activities.map {
                        ActivityResponse(
                            it.id!!,
                            it.title,
                            it.description,
                            it.type,
                            it.date,
                            it.location
                        )
                    },
                    journey.visibility
                )
            )
        )
    }

    fun deleteJourney(username: String, journeyId: Long): ResponseEntity<String> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.badRequest().body("Username does not exist")
        }

        val journey: Journey = findJourneyById(journeyId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journey does not exist")

        journeyRepository.delete(journey)
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted a journey")
    }

    fun editJourney(
        username: String,
        journeyId: Long,
        journeyRequest: JourneyRequest
    ): ResponseEntity<String> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.badRequest().body("Username does not exist")
        }

        val journey: Journey = findJourneyById(journeyId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journey does not exist")

        journey.description = journeyRequest.description
        journey.destination = destinationService.findDestinationById(journeyRequest.destinationId)!!

        journeyRepository.save(journey)

        return ResponseEntity.status(HttpStatus.OK).body(
            Json.encodeToString(
                JourneyResponse(
                    journey.id!!,
                    UserNamesResponse(
                        journey.user.username, journey.user.firstName, journey.user.lastName),
                    journey.startDate, journey.endDate!!,
                    journey.description, journey.destination.name,
                    journey.activities.map {
                        ActivityResponse(
                            it.id!!,
                            it.title,
                            it.description,
                            it.type,
                            it.date,
                            it.location
                        )
                    },
                    journey.visibility
                )
            )
        )
    }

    fun getJourney(username: String, journeyId: Long): ResponseEntity<String> {
       if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.badRequest().body("Username does not exist")
       }

       val journey: Journey = findJourneyById(journeyId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journey does not exist")

       if (journey.visibility != Visibility.PUBLIC) {
           if (journey.visibility == Visibility.DRAFT) {
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journey is still a draft")
           }
           if (journey.visibility == Visibility.FRIEND_ONLY) {
               if (username != journey.user.username && !followService.areFriends(username, journey.user.username)) {
                   return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journey is visible only by $username's friends")
               }
           }
           if (journey.visibility == Visibility.PRIVATE) {
               if (username != journey.user.username) {
                   return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journey is private")
               }
           }
       }

        return ResponseEntity.ok().body(
            Json.encodeToString(
                JourneyResponse(
                    journey.id!!,
                    UserNamesResponse(
                        journey.user.username, journey.user.firstName, journey.user.lastName),
                    journey.startDate, journey.endDate!!,
                    journey.description, journey.destination.name,
                    journey.activities.map {
                        ActivityResponse(
                            it.id!!,
                            it.title,
                            it.description,
                            it.type,
                            it.date,
                            it.location
                        )
                    },
                    journey.visibility
                )
            )
        )
    }
}