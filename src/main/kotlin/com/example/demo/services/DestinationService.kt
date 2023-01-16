package com.example.demo.services

import com.example.demo.entities.DestinationEntity
import com.example.demo.models.projections.DestinationSearchProjection
import com.example.demo.models.responseModels.Destination
import com.example.demo.models.responseModels.DestinationSearchResult
import com.example.demo.repositories.DestinationRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class DestinationService(private val destinationRepository: DestinationRepository,
                         private val featureCodeService: FeatureCodeService,
                         private val countryService: CountryService) {
    fun destinationFromEntity(destinationEntity: DestinationEntity?): Destination? {
        if ( destinationEntity == null )
            return null
        return Destination(
            destinationEntity.id!!,
            destinationEntity.latitude,
            destinationEntity.longitude,
            destinationEntity.name,
            countryService.countryFromEntity(countryService.findCountryByCountryCode(destinationEntity.country.countryCode)),
            featureCodeService.findFeatureClassMeaning(destinationEntity.featureClass),
            featureCodeService.findFeatureCodeMeaning(destinationEntity.featureCode)
        )
    }

    private fun destinationSearchResultFromProjection(destinationSearchProjection: DestinationSearchProjection): DestinationSearchResult {
        return DestinationSearchResult(
            destinationSearchProjection.id,
            featureCodeService.findFeatureCodeMeaning(destinationSearchProjection.featureCode.toString()),
            destinationSearchProjection.name,
            destinationSearchProjection.countryCountryName
        )
    }

    fun destinationSearchResultFromEntity(destinationEntity: DestinationEntity): DestinationSearchResult {
        return DestinationSearchResult(
            destinationEntity.id!!,
            featureCodeService.findFeatureClassMeaning(destinationEntity.featureClass),
            destinationEntity.name,
            destinationEntity.country.countryName
        )
    }

    fun destinationWithIdExists(id: Long): Boolean {
        return (findDestinationById(id) != null)
    }

    fun findDestinationById(id: Long?): DestinationEntity? {
        return destinationRepository.findDestinationById(id)
    }

    private fun findDestinationsByAsciiNameStartingWith(keyword: String): List<DestinationSearchProjection> {
        return destinationRepository.findDestinationsByAsciiNameStartingWith(keyword)
    }

    private fun findDestinationsByCountryNameStartingWith(keyword: String): List<DestinationSearchProjection> {
        return destinationRepository.findDestinationsByCountryCountryNameStartingWith(keyword)
    }

    private fun findDestinationsByCountryCodeStartingWith(keyword: String): List<DestinationSearchProjection> {
        return destinationRepository.findDestinationsByCountryCountryCodeStartingWith(keyword)
    }

    fun getDestinations(): ResponseEntity<List<Destination>> {
        return ResponseEntity.ok().body(
            destinationRepository.findAll().map {
                destinationFromEntity(it)!!
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

    fun searchDestinations(keyword: String): ResponseEntity<List<DestinationSearchResult>> {
        return ResponseEntity.ok().body(
            (findDestinationsByAsciiNameStartingWith(keyword) +
            findDestinationsByCountryCodeStartingWith(keyword) +
            findDestinationsByCountryNameStartingWith(keyword)).map{
                destinationSearchResultFromProjection(it)
            }
        )
    }
}