package com.example.demo.services

import com.example.demo.entities.Journey
import com.example.demo.entities.User
import com.example.demo.repositories.JourneyRepository
import com.example.demo.models.responseModels.JourneyResponse
import com.example.demo.models.PostJourney
import com.google.gson.GsonBuilder
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service


@Service
class JourneyService( val journeyRepository: JourneyRepository, val userService: UserService,
                     val destinationService: DestinationService ) {

    fun findJourneyById(id: Long): Journey? {
        return journeyRepository.findJourneysById(id)
    }

    fun findJourneysByUser(user: User): List<Journey> {
        return journeyRepository.findJourneysByUser(user)
    }

    fun journeyWithIdExists(id: Long): Boolean {
        return findJourneyById(id) != null
    }

    fun createJourney(username: String, journeyRequest: PostJourney): ResponseEntity<String> {
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

        return ResponseEntity.status(HttpStatus.CREATED).body(Json.encodeToString(
            JourneyResponse(journey.user.username, journey.startDate,
            journey.endDate!!,
            journey.description!!, journey.destination.name!!)))
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
        journeyRequest: PostJourney
    ): ResponseEntity<String> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.badRequest().body("Username does not exist")
        }

        val journey: Journey = findJourneyById(journeyId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journey does not exist")

        journey.description = journeyRequest.description
        journey.destination = destinationService.findDestinationById(journeyRequest.destinationId)!!
//        journey.activities = journeyRequest.activities

        journeyRepository.save(journey)
        val gson = GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create()

        return ResponseEntity.status(HttpStatus.OK).body(Json.encodeToString(
            JourneyResponse(journey.user.username, journey.startDate,
                journey.endDate!!,
                journey.description!!, journey.destination.name!!)))
    }

    fun getJourney(username: String, journeyId: Long): ResponseEntity<String> {
       if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.badRequest().body("Username does not exist")
        }

        val journey: Journey = findJourneyById(journeyId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journey does not exist")

        return ResponseEntity.ok().body("JourneyResponse(journey).toString()")
    }
}