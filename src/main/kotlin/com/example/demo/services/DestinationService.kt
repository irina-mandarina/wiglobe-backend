package com.example.demo.services

import com.example.demo.entities.DestinationEntity
import com.example.demo.models.responseModels.Destination
import com.example.demo.repositories.DestinationRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.io.FileReader
import java.nio.charset.StandardCharsets

@Service
class DestinationService(private val destinationRepository: DestinationRepository,
                         private val featureCodeService: FeatureCodeService) {
    fun destinationFromEntity(destinationEntity: DestinationEntity): Destination {
        return Destination(
            destinationEntity.latitude,
            destinationEntity.longitude,
            destinationEntity.name,
            destinationEntity.countryCode,
            featureCodeService.findFeatureClassMeaning(destinationEntity.featureClass),
            featureCodeService.findFeatureCodeMeaning(destinationEntity.featureCode)
        )
    }

    fun destinationWithIdExists(id: Long): Boolean {
        return (findDestinationById(id) != null)
    }

    fun findDestinationById(id: Long): DestinationEntity? {
        return destinationRepository.findDestinationById(id)
    }

    fun findDestinationByName(name: String): DestinationEntity? {
        return destinationRepository.findDestinationByName(name)
    }

    fun getDestinations(): ResponseEntity<List<Destination>> {
        return ResponseEntity.ok().body(
            destinationRepository.findAll().map {
                destinationFromEntity(it)
            }
        )
    }
}