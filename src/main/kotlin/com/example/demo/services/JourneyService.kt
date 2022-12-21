package com.example.demo.services

import com.example.demo.entities.JourneyEntity
import com.example.demo.entities.UserEntity
import com.example.demo.repositories.JourneyRepository
import com.example.demo.models.responseModels.Journey
import com.example.demo.models.requestModels.JourneyRequest
import com.example.demo.models.responseModels.Activity
import com.example.demo.models.responseModels.UserNames
import com.example.demo.types.Visibility
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service


@Service
class JourneyService(private val journeyRepository: JourneyRepository, private val userService: UserService,
                     private val destinationService: DestinationService, private val followService: FollowService) {

    fun journeyFromEntity(journeyEntity: JourneyEntity): Journey {
        return Journey (
            journeyEntity.id!!,
            userService.userNames(journeyEntity.user),
            journeyEntity.startDate, journeyEntity.endDate!!,
            journeyEntity.description,
            destinationService.destinationFromEntity(journeyEntity.destination),
            journeyEntity.activities.map {
                Activity(
                    it.id!!,
                    it.title,
                    it.description,
                    it.type,
                    it.date,
                    it.location
                )
            },
            journeyEntity.visibility
        )
    }

    fun findJourneyById(id: Long): JourneyEntity? {
        return journeyRepository.findJourneysById(id)
    }

    fun findJourneysByUser(user: UserEntity): List<JourneyEntity> {
        return journeyRepository.findJourneysByUser(user)
    }

    fun findJourneyEntitiesByUserNot(user: UserEntity): List<JourneyEntity> {
        return journeyRepository.findJourneyEntitiesByUserNot(user)
    }

    fun journeyWithIdExists(id: Long): Boolean {
        return findJourneyById(id) != null
    }

    fun createJourney(username: String, journeyRequest: JourneyRequest): ResponseEntity<Journey> {
        if (username.isBlank()) {
            return ResponseEntity.badRequest().header(
                "message", "Missing request parameter: username")
                .body(null)
        }

        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.badRequest().header(
                "message", "Username does not exist")
                .body(null)
        }

        val journey = JourneyEntity()
        journey.user = userService.findUserByUsername(username)!!
        journey.destination = destinationService.findDestinationById(journeyRequest.destinationId)!!
        journey.startDate = journeyRequest.startDate
        journey.endDate = journeyRequest.endDate
        journey.description = journeyRequest.description

        journeyRepository.save(journey)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            journeyFromEntity(journey)
        )
    }

    fun deleteJourney(username: String, journeyId: Long): ResponseEntity<String> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.badRequest().header(
                "message", "Username does not exist")
                .body(null)
        }

        val journey: JourneyEntity = findJourneyById(journeyId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message", "Journey does not exist")
                .body(null)

        journeyRepository.delete(journey)
        return ResponseEntity.status(HttpStatus.OK).header(
                "message", "Successfully deleted a journey")
            .body(null)
    }

    fun editJourney(
        username: String,
        journeyId: Long,
        journeyRequest: JourneyRequest
    ): ResponseEntity<Journey> {
        if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.badRequest().header(
                "message", "Username does not exist")
                .body(null)
        }

        val journey: JourneyEntity = findJourneyById(journeyId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message", "Journey does not exist")
                .body(null)

        journey.description = journeyRequest.description
        journey.destination = destinationService.findDestinationById(journeyRequest.destinationId)!!

        journeyRepository.save(journey)

        return ResponseEntity.status(HttpStatus.OK).body(
            journeyFromEntity(journey)
        )
    }

    fun getJourney(username: String, journeyId: Long): ResponseEntity<Journey> {
       if (!userService.userWithUsernameExists(username)) {
            return ResponseEntity.badRequest().header(
                "message", "Username does not exist").body(null)
       }

       val journey: JourneyEntity = findJourneyById(journeyId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message", "Journey does not exist")
                .body(null)

       if (journey.visibility != Visibility.PUBLIC) {
           if (journey.visibility == Visibility.DRAFT) {
               return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message", "Journey is still a draft")
                   .body(null)
           }
           if (journey.visibility == Visibility.FRIEND_ONLY) {
               if (username != journey.user.username && !followService.areFriends(username, journey.user.username)) {
                   return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message", "Journey is visible only by $username's friends")
                       .body(null)
               }
           }
           if (journey.visibility == Visibility.PRIVATE) {
               if (username != journey.user.username) {
                   return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message", "Journey is private")
                       .body(null)
               }
           }
       }

        return ResponseEntity.ok().body(
            journeyFromEntity(journey)
        )
    }

    fun getJourneysByUser(username: String, requestedJourneysOwnerUsername: String): ResponseEntity<List<Journey>> {
        if (!userService.userWithUsernameExists(username) || !userService.userWithUsernameExists(requestedJourneysOwnerUsername)) {
            return ResponseEntity.badRequest().header(
                "message", "Username does not exist")
                .body(null)
        }

        val user = userService.findUserByUsername(requestedJourneysOwnerUsername)!!
        var journeys = findAllByUserAndVisibilityIsIn(user, listOf(Visibility.PUBLIC, Visibility.FRIEND_ONLY))

        // check if the requester and the owner are not friends. otherwise all journeys should be returned
        if (!followService.areFriends(username, requestedJourneysOwnerUsername)) {
            journeys = findAllByUserAndVisibility(user, Visibility.PUBLIC)
        }

        return ResponseEntity.ok().body(
            journeys.map {
                journeyFromEntity( it )
            }
        )
    }

    fun findAllByUserAndVisibility(user: UserEntity, visibility: Visibility): List<JourneyEntity> {
        return journeyRepository.findAllByUserAndVisibility(user, visibility)
    }

    fun findAllByUserAndVisibilityIsIn(user: UserEntity, visibilities: List<Visibility>): List<JourneyEntity> {
        return journeyRepository.findAllByUserAndVisibilityIsIn(user, visibilities)
    }
}