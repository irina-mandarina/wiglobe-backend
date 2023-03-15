package com.example.demo.services

import com.example.demo.entities.JourneyEntity
import com.example.demo.entities.UserEntity
import com.example.demo.repositories.JourneyRepository
import com.example.demo.models.responseModels.Journey
import com.example.demo.models.requestModels.JourneyRequest
import com.example.demo.models.responseModels.Activity
import com.example.demo.models.responseModels.Destination
import com.example.demo.types.Visibility
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service


@Service
class JourneyService(private val journeyRepository: JourneyRepository, private val userService: UserService,
                     private val destinationService: DestinationService, private val followService: FollowService,
                     private val journeyImageService: JourneyImagePathService) {

    public fun journeyFromEntity(journeyEntity: JourneyEntity): Journey {
        var destination: Destination? = null
        if (journeyEntity.destination != null) {
            destination = destinationService.destinationFromEntity((journeyEntity.destination!!))
        }
        return Journey (
            journeyEntity.id,
            userService.userNames(journeyEntity.user),
            journeyEntity.postedOn,
            journeyEntity.startDate,
            journeyEntity.endDate,
            journeyEntity.description,
            destination,
            journeyEntity.activities.map {
                Activity(
                    it.id,
                    it.description,
                    it.type,
                    it.date,
                    it.image
                )
            },
            journeyEntity.visibility,
            journeyImageService.findAllByJourney(journeyEntity)
                .map {
                    it.filepath
                }
        )
    }

    fun createJourney(username: String, journeyRequest: JourneyRequest): ResponseEntity<Journey?> {
        if (journeyRequest.visibility != Visibility.DRAFT &&
            (journeyRequest.startDate == null || journeyRequest.endDate == null || journeyRequest.destinationId == null)
        ){
            // nulls are not ok
            return ResponseEntity.badRequest().body(null)
        }

        var journey: JourneyEntity
        if (journeyRequest.id != null) {
            if (findJourneyById(journeyRequest.id) != null){
                journey = findJourneyById(journeyRequest.id)!!
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
            }
        } else {
            journey = JourneyEntity()
            journey.user = userService.findUserByUsername(username)!!
        }

        if (journeyRequest.destinationId != null) {
            journey.destination = destinationService.findDestinationById(journeyRequest.destinationId)
        }
        journey.startDate = journeyRequest.startDate
        journey.endDate = journeyRequest.endDate
        journey.description = journeyRequest.description

        if (journeyRequest.visibility == null) {
            journey.visibility = Visibility.PRIVATE
        }
        else {
            journey.visibility = journeyRequest.visibility
        }
        // first saving the journey to get an entity with an id
        journey = journeyRepository.save(journey)
//        // saving the image paths
        journeyImageService.save(journeyRequest.images, journey)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            journeyFromEntity(journey)
        )
    }

    fun deleteJourney(username: String, journeyId: Long): ResponseEntity<String> {
        val journey: JourneyEntity = findJourneyById(journeyId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null)

        journeyRepository.delete(journey)
        return ResponseEntity.status(HttpStatus.OK)
            .body(null)
    }

    fun editJourney(
        username: String,
        journeyId: Long,
        journeyRequest: JourneyRequest
    ): ResponseEntity<Journey> {
        val journey: JourneyEntity = findJourneyById(journeyId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND)
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
       val journey: JourneyEntity = findJourneyById(journeyId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null)

       if ( !isJourneyVisibleByUser(journey, userService.findUserByUsername(username)!!) ) {
           return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null)
       }

        return ResponseEntity.ok().body(
            journeyFromEntity(journey)
        )
    }

    fun getJourneysByUser(username: String, requestedJourneysOwnerUsername: String): ResponseEntity<List<Journey>> {
        if (!userService.userWithUsernameExists(requestedJourneysOwnerUsername)) {
            return ResponseEntity.badRequest().header(
                "message", "Username does not exist")
                .body(null)
        }

        val journeys: List<JourneyEntity> =
            if (username == requestedJourneysOwnerUsername) {
                // requester requests their own journeys
                findAllByUserUsername(requestedJourneysOwnerUsername)
            }
            else if (followService.areFriends(username, requestedJourneysOwnerUsername)) {
                // they are friends
                findAllByUserUsernameAndVisibilityIsIn(requestedJourneysOwnerUsername, listOf(Visibility.PUBLIC, Visibility.FRIEND_ONLY, Visibility.PRIVATE))
            }
            else if (followService.isFollowing(username, requestedJourneysOwnerUsername)) {
                findAllByUserUsernameAndVisibilityIsIn(requestedJourneysOwnerUsername, listOf(Visibility.PUBLIC, Visibility.PRIVATE))
            }
            else {
                findAllByUserUsernameAndVisibility(requestedJourneysOwnerUsername, Visibility.PUBLIC)
            }

        val user = userService.findUserByUsername(username)!!

        return ResponseEntity.ok().body(
            journeys
                .filter { isJourneyVisibleByUser(it, user) }
                .map {
                journeyFromEntity( it )
            }
        )
    }

    fun getJourneysByDestination(username: String, destinationId: Long): ResponseEntity<List<Journey>> {
        val journeys = findAllByDestinationIdAndVisibilityNot(destinationId, Visibility.DRAFT).toMutableList()
        val user = userService.findUserByUsername(username)!!

        return ResponseEntity.ok().body(
            journeys
                .filter { isJourneyVisibleByUser(it, user) }
                .map {
                journeyFromEntity(it)
            }
        )
    }

    fun getDrafts(username: String): ResponseEntity<List<Journey>> {
        return ResponseEntity.ok().body(
            findAllByUserUsernameAndVisibility(username, Visibility.DRAFT)
                .map {
                    journeyFromEntity(it)
                }
        )
    }

    private fun findAllByUserUsernameAndVisibilityIsIn(username: String, visibilities: List<Visibility>): List<JourneyEntity> {
        return journeyRepository.findAllByUserUsernameAndVisibilityIsIn(username, visibilities)
    }

    private fun findAllByUserUsernameAndVisibility(username: String, visibility: Visibility): List<JourneyEntity> {
        return journeyRepository.findAllByUserUsernameAndVisibility(username, visibility)
    }

    fun findJourneyById(id: Long): JourneyEntity? {
        return journeyRepository.findJourneyEntityById(id)
    }

    fun findAllByUserUsername(username: String): List<JourneyEntity> {
        return journeyRepository.findAllByUserUsernameOrderByPostedOnDesc(username)
    }

    fun findAllByUserNotAndVisibility(user: UserEntity, visibility: Visibility): List<JourneyEntity> {
        return journeyRepository.findAllByUserNotAndVisibility(user, visibility)
    }

    fun journeyWithIdExists(id: Long): Boolean {
        return findJourneyById(id) != null
    }

    fun findAllVisibleByUserAndNotByUser(user: UserEntity): List<JourneyEntity> {
        var journeys = findAllByUserNotAndVisibilityNot(user, Visibility.DRAFT).toMutableList()
        return journeys
            .filter { isJourneyVisibleByUser(it, user) }
    }

    fun findAllByUserNotAndVisibilityNot(user: UserEntity, visibility: Visibility): List<JourneyEntity> {
        return journeyRepository.findAllByUserNotAndVisibilityNot(user, visibility)
    }

    fun findAllByDestinationIdAndVisibilityNot(destinationId: Long,
                                               visibility: Visibility): List<JourneyEntity> {
        return journeyRepository.findAllByDestinationIdAndVisibilityNot(destinationId, visibility)
    }

    fun searchJourneys(username: String, keyword: String, pageNumber: Int, pageSize: Int): ResponseEntity<List<Journey>> {
        val user = userService.findUserByUsername(username)!!
        val result = journeyRepository.findAllByDescriptionContainingOrderByPostedOnDesc(keyword, PageRequest.of(pageNumber, pageSize))
            .filter { isJourneyVisibleByUser( it, user ) }
        val startIndex = pageNumber * pageSize
        var endIndex = (pageNumber + 1) * pageSize

        if (startIndex < result.size) {
            if (endIndex >= result.size)
                endIndex = result.size - 1
            return ResponseEntity.ok().body(
                result
                    .subList(startIndex, endIndex)
                    .map { journeyFromEntity( it ) }
            )
        }
        else {
            return ResponseEntity.ok().body(
                listOf()
            )
        }

    }

    fun isJourneyVisibleByUser(journey: JourneyEntity, user: UserEntity): Boolean {
        if (journey.visibility === Visibility.PUBLIC)
            return true
        if (journey.visibility === Visibility.PRIVATE) {
            if (user.isFollowing(journey.user)) {
                return true
            }
        }
        if (journey.visibility === Visibility.FRIEND_ONLY) {
            if (user.isFriendsWith(journey.user)) {
                return true
            }
        }
        if (journey.user === user) {
            return true
        }
        return false
    }
}