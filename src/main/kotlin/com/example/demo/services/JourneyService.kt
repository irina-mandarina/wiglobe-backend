package com.example.demo.services

import com.example.demo.entities.JourneyEntity
import com.example.demo.entities.UserEntity
import com.example.demo.repositories.JourneyRepository
import com.example.demo.models.responseModels.Journey
import com.example.demo.models.requestModels.JourneyRequest
import com.example.demo.models.responseModels.Activity
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
            journeyEntity.startDate,
            journeyEntity.endDate,
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

    fun createJourney(username: String, journeyRequest: JourneyRequest): ResponseEntity<Journey?> {
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

        if (journeyRequest.visibility != Visibility.DRAFT &&
            (journeyRequest.startDate == null || journeyRequest.endDate == null || journeyRequest.destinationId == null)
        ){
            // nulls are not ok
            return ResponseEntity.badRequest().body(null)
        }

        val journey = JourneyEntity()
        journey.user = userService.findUserByUsername(username)!!
        journey.destination = destinationService.findDestinationById(journeyRequest.destinationId!!)
        journey.startDate = journeyRequest.startDate
        journey.endDate = journeyRequest.endDate
        journey.description = journeyRequest.description

        if (journeyRequest.visibility == null) {
            journey.visibility = Visibility.PRIVATE
        }
        else {
            journey.visibility = journeyRequest.visibility
        }
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

        if (journeyRequest.visibility != Visibility.DRAFT &&
            (journeyRequest.startDate == null || journeyRequest.endDate == null || journeyRequest.destinationId == null)
        ){
            // nulls are not ok
            return ResponseEntity.badRequest().body(null)
        }

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

       if (journey.visibility != Visibility.PUBLIC && journey.user.username != username) {
           if (journey.visibility == Visibility.DRAFT ) {
               return ResponseEntity.status(HttpStatus.FORBIDDEN)
                   .body(null)
           }
           if (journey.visibility == Visibility.FRIEND_ONLY) {
               if (!followService.areFriends(username, journey.user.username)) {
                   return ResponseEntity.status(HttpStatus.FORBIDDEN)
                       .body(null)
               }
           }
           if (journey.visibility == Visibility.PRIVATE) {
               if (!followService.isFollowing(username, journey.user.username)) {
                   return ResponseEntity.status(HttpStatus.FORBIDDEN)
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

        val journeys: List<JourneyEntity> =
            if (!followService.areFriends(username, requestedJourneysOwnerUsername)) {
                // check if the requester and the owner are friends. otherwise all journeys should be returned
                findJourneyEntitiesByUserUsernameAndVisibility(username, Visibility.PUBLIC)
            }
            else if (username === requestedJourneysOwnerUsername) {
                // requester requests their own journeys
                findJourneysByUserUsername(username)
            }
            else {
                // they are friends
                findAllByUserUsernameAndVisibilityIsIn(username, listOf(Visibility.PUBLIC, Visibility.FRIEND_ONLY))
            }

        return ResponseEntity.ok().body(
            journeys.map {
                journeyFromEntity( it )
            }
        )
    }

    fun getDrafts(username: String): ResponseEntity<List<Journey>> {
        return ResponseEntity.ok().body(
            findJourneyEntitiesByUserUsernameAndVisibility(username, Visibility.DRAFT)
                .map {
                    journeyFromEntity(it)
                }
        )
    }

    private fun findAllByUserUsernameAndVisibilityIsIn(username: String, visibilities: List<Visibility>): List<JourneyEntity> {
        return journeyRepository.findAllByUserUsernameAndVisibilityIsIn(username, visibilities)
    }

    private fun findJourneyEntitiesByUserUsernameAndVisibility(username: String, visibility: Visibility): List<JourneyEntity> {
        return journeyRepository.findJourneyEntitiesByUserUsernameAndVisibility(username, visibility)
    }

    fun findJourneyById(id: Long): JourneyEntity? {
        return journeyRepository.findJourneysById(id)
    }

    fun findJourneysByUserUsername(username: String): List<JourneyEntity> {
        return journeyRepository.findJourneysByUserUsername(username)
    }

    fun findJourneyEntitiesByUserNot(user: UserEntity): List<JourneyEntity> {
        return journeyRepository.findJourneyEntitiesByUserNot(user)
    }

    fun journeyWithIdExists(id: Long): Boolean {
        return findJourneyById(id) != null
    }
}