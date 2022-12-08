package com.example.demo.controllers

import com.example.demo.models.responseModels.Destination
import com.example.demo.services.DestinationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DestinationController(private val destinationService: DestinationService) {
    @GetMapping("/destinations")
    fun getDestinations(): ResponseEntity<List<Destination>> {
        return destinationService.getDestinations();
    }
}