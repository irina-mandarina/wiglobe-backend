package com.example.demo.services.serviceImplementations

import com.example.demo.entities.Destination
import com.example.demo.repositories.DestinationRepository
import com.example.demo.services.DestinationService
import org.springframework.stereotype.Service

@Service
class DestinationServiceImpl(val destinationRepository: DestinationRepository):
    DestinationService {
    override fun destinationWithIdExists(id: Long): Boolean {
        return (findDestinationById(id) != null)
    }

    override fun findDestinationById(id: Long): Destination? {
        return destinationRepository.findDestinationById(id)
    }

    override fun findDestinationByName(name: String): Destination? {
        return destinationRepository.findDestinationByName(name)
    }
}