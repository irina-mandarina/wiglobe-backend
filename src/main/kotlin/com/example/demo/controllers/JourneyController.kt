package com.example.demo.controllers

import com.example.demo.models.requestModels.JourneyRequest
import com.example.demo.models.responseModels.Journey
import com.example.demo.recommender.JourneyScoreCalculator
import com.example.demo.services.CommentService
import com.example.demo.services.JourneyService
import com.example.demo.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class JourneyController(private val journeyService: JourneyService, private val commentService: CommentService,
                        private val userService: UserService, private val journeyScoreCalculator: JourneyScoreCalculator) {
    @PostMapping("/journeys")
    fun createJourney(@RequestHeader username: String,
                      @RequestBody journeyRequest: JourneyRequest): ResponseEntity<Journey>  {
        return journeyService.createJourney(username, journeyRequest)
    }
//
//    @PostMapping("/journeys/new")
//    fun createEmptyJourney(@RequestHeader username: String): ResponseEntity<String>  {
//        return journeyService.createEmptyJourney(username)
//    }

    @DeleteMapping("/journeys/{journeyId}")
    fun deleteJourney(@RequestHeader username: String,
                      @PathVariable journeyId: Long): ResponseEntity<String>  {
        return journeyService.deleteJourney(username, journeyId)
    }

    @PutMapping("/journeys/{journeyId}")
    fun editJourney(@RequestHeader username: String, @RequestBody journeyRequest: JourneyRequest,
                    @PathVariable journeyId: Long): ResponseEntity<Journey> {
        return journeyService.editJourney(username, journeyId, journeyRequest)
    }

    @GetMapping("/journeys/{journeyId}")
    fun getJourney(@RequestHeader username: String,
                   @PathVariable journeyId: Long): ResponseEntity<Journey> {
        return journeyService.getJourney(username, journeyId)
    }

    @GetMapping("/journeys")
    fun getJourneyRecommendations(@RequestHeader username: String): Double {
//        val journeyScoreCalculator = JourneyScoreCalculator(commentService, userService)
        return journeyScoreCalculator.calculateScoreForJourneyForUser(username)
    }

}
