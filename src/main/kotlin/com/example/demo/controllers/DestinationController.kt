package com.example.demo.controllers

import com.example.demo.models.responseModels.Destination
import com.example.demo.models.responseModels.DestinationSearchResult
import com.example.demo.services.DestinationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
class DestinationController(private val destinationService: DestinationService) {
    @GetMapping("/destinations/{destinationId}")
    fun getDestination(@PathVariable destinationId: Long): ResponseEntity<Destination> {
        return destinationService.getDestination(destinationId);
    }

    @GetMapping("/destinations")
    fun searchDestinations(@RequestParam search: String): ResponseEntity<List<DestinationSearchResult>> {
        return destinationService.searchDestinations(search);
    }
}