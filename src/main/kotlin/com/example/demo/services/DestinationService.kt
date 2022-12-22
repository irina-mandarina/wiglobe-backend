package com.example.demo.services

import com.example.demo.entities.DestinationEntity
import com.example.demo.models.responseModels.Destination
import com.example.demo.repositories.DestinationRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class DestinationService(private val destinationRepository: DestinationRepository,
                         private val featureCodeService: FeatureCodeService,
                         private val countryService: CountryService) {
    fun destinationFromEntity(destinationEntity: DestinationEntity): Destination {
        return Destination(
            destinationEntity.latitude,
            destinationEntity.longitude,
            destinationEntity.name,
            countryService.countryFromEntity(countryService.findCountryByCountryCode(destinationEntity.countryCode)),
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

    fun getDestination(destinationId: Long): ResponseEntity<Destination> {
        return ResponseEntity.ok().body (
            destinationFromEntity(
                findDestinationById(destinationId)!!
            )
        )
    }
}