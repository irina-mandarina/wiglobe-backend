package com.example.demo.services

import com.example.demo.entities.DestinationEntity
import com.example.demo.models.projections.DestinationSearchProjection
import com.example.demo.models.responseModels.Destination
import com.example.demo.models.responseModels.DestinationSearchResult
import com.example.demo.repositories.DestinationRepository
import com.example.demo.types.InterestKeyEntityType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class DestinationService(private val destinationRepository: DestinationRepository,
                         private val featureCodeService: FeatureCodeService,
                         private val countryService: CountryService,
                         private val interestsService: InterestsService) {
    fun destinationFromEntity(destinationEntity: DestinationEntity?): Destination? {
        if ( destinationEntity == null )
            return null
        return Destination(
            destinationEntity.id!!,
            destinationEntity.latitude,
            destinationEntity.longitude,
            destinationEntity.name,
            countryService.countryFromEntity(destinationEntity.country),
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
        if (destinationEntity.country == null) {
            return DestinationSearchResult(
                destinationEntity.id!!,
                featureCodeService.findFeatureClassMeaning(destinationEntity.featureClass),
                destinationEntity.name,
                null
            )
        }
        return DestinationSearchResult(
            destinationEntity.id!!,
            featureCodeService.findFeatureClassMeaning(destinationEntity.featureClass),
            destinationEntity.name,
            destinationEntity.country!!.countryName
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

    fun findAllByFeatureCodeCodeIn(featureCodes: List<String>): List<DestinationEntity> {
        return destinationRepository.findAllByFeatureCodeCodeIn(featureCodes)
    }

    fun findAllByFeatureClassIn(featureClasses: List<String>): List<DestinationEntity> {
        return destinationRepository.findAllByFeatureClassIn(featureClasses)
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

    fun recommendDestinationsToUser(username: String): ResponseEntity<List<Destination>> {
        val featureClassInterests = interestsService
            .findAllByEntityAndUserUsernameOrderByValueDesc(InterestKeyEntityType.FEATURE_CLASS, username)
            .map {
                it.key
            }
        val featureCodeInterests = interestsService
            .findAllByEntityAndUserUsernameOrderByValueDesc(InterestKeyEntityType.FEATURE_CODE, username)
            .map {
                it.key
            }
        val recommendations = findAllByFeatureClassIn(featureClassInterests).toMutableList()
        recommendations += findAllByFeatureCodeCodeIn(featureCodeInterests)

        return ResponseEntity.ok().body(
            recommendations.map {
                destinationFromEntity(it)!!
            }
        )
    }
}