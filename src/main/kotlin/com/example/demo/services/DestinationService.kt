package com.example.demo.services

import com.example.demo.entities.Destination
import com.example.demo.repositories.DestinationRepository
import org.springframework.stereotype.Service

@Service
class DestinationService(val destinationRepository: DestinationRepository) {
    fun destinationWithIdExists(id: Long): Boolean {
        return (findDestinationById(id) != null)
    }

    fun findDestinationById(id: Long): Destination? {
        return destinationRepository.findDestinationById(id)
    }

    fun findDestinationByName(name: String): Destination? {
        return destinationRepository.findDestinationByName(name)
    }
}