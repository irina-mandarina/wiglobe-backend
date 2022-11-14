package com.example.demo.controllers

import com.example.demo.requestEntities.JourneyRequest
import com.example.demo.services.JourneyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class JourneyController {
    private val journeyService: JourneyService = TODO()

    @PostMapping("/journeys")
    fun createJourney(@RequestHeader username: String, @RequestBody journeyRequest: JourneyRequest): ResponseEntity<String>  {
        return journeyService.createJourney(username, journeyRequest)
    }

    @DeleteMapping("/journeys/{journeyId}")
    fun deleteJourney(@RequestHeader username: String, @PathVariable journeyId: Long): ResponseEntity<String>  {
        return journeyService.deleteJourney(username, journeyId)
    }

    @PutMapping("/journeys/{journeyId}")
    fun editJourney(@RequestHeader username: String, @RequestBody journeyRequest: JourneyRequest,
                    @PathVariable journeyId: Long
    )  : ResponseEntity<String> {
        return journeyService.editJourney(username, journeyRequest, journeyId)
    }

    @GetMapping("/journeys/{journeyId}")
    fun getJourney(@RequestHeader username: String, @PathVariable journeyId: Long): ResponseEntity<String> {
        return journeyService.getJourney(username, journeyId)
    }

}