package com.example.demo.services.serviceImplementations

import com.example.demo.entities.Journey
import com.example.demo.entities.User
import com.example.demo.repositories.JourneyRepository
import com.example.demo.requestEntities.JourneyRequest
import com.example.demo.services.DestinationService
import com.example.demo.services.JourneyService
import com.example.demo.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class JourneyServiceImpl(val journeyRepository: JourneyRepository, val userService: UserService, val destinationService: DestinationService): JourneyService {

    override fun findJourneyById(id: Long): Journey? {
        return journeyRepository.findJourneysById(id)
    }

    override fun findJourneysByUser(user: User): List<Journey> {
        return journeyRepository.findJourneysByUser(user)
    }

    override fun journeyWithIdExists(id: Long): Boolean {
        return findJourneyById(id) != null
    }

    override fun createJourney(username: String, journeyRequest: JourneyRequest): ResponseEntity<String> {
        if (username.isBlank()) {
            return ResponseEntity.badRequest().body("Missing request parameter: username")
        }

        if (userService.userWithUsernameExists(username)) {
            return ResponseEntity.badRequest().body("Username does not exist")
        }

        val journey = Journey()
        journey.user = userService.findUserByUsername(username)!!
        journey.destination = destinationService.findDestinationByName(journeyRequest.destination)!!
        journey.startDate = journeyRequest.startDate
        journey.endDate = journeyRequest.endDate
        journey.activities = journeyRequest.activities

        journeyRepository.save(journey)

        return ResponseEntity.status(HttpStatus.CREATED).body(JourneyRequest(journey).toString())
    }

    override fun deleteJourney(username: String, journeyId: Long): ResponseEntity<String> {
        if (username.isBlank()) {
            return ResponseEntity.badRequest().body("Missing request parameter: username")
        }

        if (userService.userWithUsernameExists(username)) {
            return ResponseEntity.badRequest().body("Username does not exist")
        }

        val journey: Journey = findJourneyById(journeyId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journey does not exist")

        journeyRepository.delete(journey)
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted a journey")
    }

    override fun editJourney(
        username: String,
        journeyRequest: JourneyRequest
    ): ResponseEntity<String> {
        if (username.isBlank()) {
            return ResponseEntity.badRequest().body("Missing request parameter: username")
        }

        if (userService.userWithUsernameExists(username)) {
            return ResponseEntity.badRequest().body("Username does not exist")
        }

        val journey: Journey = findJourneyById(journeyRequest.id!!)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journey does not exist")

        journey.description = journeyRequest.description
        journey.destination = destinationService.findDestinationByName(journeyRequest.destination)!!
        journey.activities = journeyRequest.activities

        journeyRepository.save(journey)
        return ResponseEntity.status(HttpStatus.OK).body(
            JourneyRequest(findJourneyById(journeyRequest.id!!)!!).toString())
    }

    override fun getJourney(username: String, journeyId: Long): ResponseEntity<String> {
        if (username.isBlank()) {
            return ResponseEntity.badRequest().body("Missing request parameter: username")
        }

        if (userService.userWithUsernameExists(username)) {
            return ResponseEntity.badRequest().body("Username does not exist")
        }

        val journey: Journey = findJourneyById(journeyId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journey does not exist")

        return ResponseEntity.ok().body(JourneyRequest(findJourneyById(journeyId)!!).toString())
    }
}