package com.example.demo.controllers

import com.example.demo.requestEntities.PostJourney
import com.example.demo.services.JourneyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class JourneyController(val journeyService: JourneyService) {
    @PostMapping("/journeys")
    fun createJourney(@RequestHeader username: String, @RequestBody journeyRequest: PostJourney): ResponseEntity<String>  {
        return journeyService.createJourney(username, journeyRequest)
    }
//
//    @PostMapping("/journeys/new")
//    fun createEmptyJourney(@RequestHeader username: String): ResponseEntity<String>  {
//        return journeyService.createEmptyJourney(username)
//    }

    @DeleteMapping("/journeys/{journeyId}")
    fun deleteJourney(@RequestHeader username: String, @PathVariable journeyId: Long): ResponseEntity<String>  {
        return journeyService.deleteJourney(username, journeyId)
    }

    @PutMapping("/journeys/{journeyId}")
    fun editJourney(@RequestHeader username: String, @RequestBody journeyRequest: PostJourney,
                    @PathVariable journeyId: Long
    )  : ResponseEntity<String> {
        return journeyService.editJourney(username, journeyId, journeyRequest)
    }

    @GetMapping("/journeys/{journeyId}")
    fun getJourney(@RequestHeader username: String, @PathVariable journeyId: Long): ResponseEntity<String> {
        return journeyService.getJourney(username, journeyId)
    }

}
