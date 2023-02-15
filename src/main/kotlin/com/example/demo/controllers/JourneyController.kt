package com.example.demo.controllers

import com.example.demo.models.requestModels.JourneyRequest
import com.example.demo.models.responseModels.Journey
import com.example.demo.recommender.JourneyRecommender
import com.example.demo.services.CommentService
import com.example.demo.services.JourneyService
import com.example.demo.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("http://localhost:3000")
class JourneyController(private val journeyService: JourneyService,
                        private val journeyScoreCalculator: JourneyRecommender
) {
    @PostMapping("/journeys")
    fun createJourney(@RequestAttribute username: String,
                      @RequestBody journeyRequest: JourneyRequest): ResponseEntity<Journey?>  {
        return journeyService.createJourney(username, journeyRequest)
    }

    @DeleteMapping("/journeys/{journeyId}")
    fun deleteJourney(@RequestAttribute username: String,
                      @PathVariable journeyId: Long): ResponseEntity<String>  {
        return journeyService.deleteJourney(username, journeyId)
    }

    @PutMapping("/journeys/{journeyId}")
    fun editJourney(@RequestAttribute username: String, @PathVariable journeyId: Long,
                    @RequestBody journeyRequest: JourneyRequest): ResponseEntity<Journey> {
        return journeyService.editJourney(username, journeyId, journeyRequest)
    }

    @GetMapping("/journeys/{journeyId}")
    fun getJourney(@RequestAttribute username: String,
                   @PathVariable journeyId: Long): ResponseEntity<Journey> {
        return journeyService.getJourney(username, journeyId)
    }

    @GetMapping("/journeys")
    fun getJourneyRecommendations(@RequestAttribute username: String): List<Journey> {
        val journeyRecommendations = journeyScoreCalculator.recommendForUser(username)
            .toList().sortedByDescending { (_, value) -> value}.toMap()

        for (set in journeyRecommendations) {
            println("${set.key}: ${set.value} ")
        }
        return journeyRecommendations.map {
            journeyService.journeyFromEntity( it.key )
        }
    }

    @GetMapping("/{username}/journeys")
    fun getJourneysByUser(@RequestAttribute username: String,
                          @PathVariable("username") requestedJourneysOwnerUsername: String): ResponseEntity<List<Journey>> {
        return journeyService.getJourneysByUser(username, requestedJourneysOwnerUsername)
    }

    @GetMapping("/{username}/journeys/drafts")
    fun getDrafts(@RequestAttribute username: String): ResponseEntity<List<Journey>> {
        return journeyService.getDrafts(username)
    }

    @GetMapping("/destinations/{destinationId}/journeys")
    fun getJourneysByDestination(@RequestAttribute username: String, @PathVariable destinationId: Long): ResponseEntity<List<Journey>> {
        return journeyService.getJourneysByDestination(username, destinationId)
    }
}
