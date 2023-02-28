package com.example.demo.controllers

import com.example.demo.models.responseModels.Destination
import com.example.demo.models.responseModels.DestinationSearchResult
import com.example.demo.services.DestinationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("http://localhost:3000")
class DestinationController(private val destinationService: DestinationService) {
    @GetMapping("/destinations/{destinationId}")
    fun getDestination(@PathVariable destinationId: Long): ResponseEntity<Destination> {
        return destinationService.getDestination(destinationId);
    }

    @GetMapping("/destinations/search")
    fun searchDestinations(@RequestParam keyword: String, @RequestParam pageNumber: Int, @RequestParam pageSize: Int): ResponseEntity<List<DestinationSearchResult>> {
        return destinationService.searchDestinations(keyword, pageNumber, pageSize);
    }

    // not tested
    @GetMapping("/destinations")
    fun recommendDestinations(@RequestAttribute username: String,
                              @RequestParam pageNumber: Int, @RequestParam pageSize: Int): ResponseEntity<List<Destination>> {
        return destinationService.recommendDestinationsToUser(username, pageNumber, pageSize)
    }
}