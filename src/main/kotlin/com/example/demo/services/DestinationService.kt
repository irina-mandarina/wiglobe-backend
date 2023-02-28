package com.example.demo.services

import com.example.demo.entities.DestinationEntity
import com.example.demo.models.projections.DestinationSearchProjection
import com.example.demo.models.responseModels.Destination
import com.example.demo.models.responseModels.DestinationSearchResult
import com.example.demo.repositories.DestinationRepository
import com.example.demo.types.InterestKeyEntityType
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestParam

@Service
class DestinationService(private val destinationRepository: DestinationRepository,
                         private val featureCodeService: FeatureCodeService,
                         private val countryService: CountryService,
                         private val interestsService: InterestsService) {
    fun destinationFromEntity(destinationEntity: DestinationEntity?): Destination? {
        if ( destinationEntity == null )
            return null
        var averageRating: Double = 0.0
        if (destinationEntity.reviews.isNotEmpty()) {
            destinationEntity.reviews.map {averageRating += it.starRating}
            averageRating /= destinationEntity.reviews.size
        }
        return Destination(
            destinationEntity.id!!,
            destinationEntity.latitude,
            destinationEntity.longitude,
            destinationEntity.name,
            countryService.countryFromEntity(destinationEntity.country),
            featureCodeService.findFeatureClassMeaning(destinationEntity.featureClass),
            featureCodeService.findFeatureCodeMeaning(destinationEntity.featureCode),
            destinationEntity.journeys.size,
            averageRating,
            destinationEntity.reviews.size
        )
    }

    fun destinationSearchResultFromProjection(destinationSearchProjection: DestinationSearchProjection): DestinationSearchResult {
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

    fun findAllByFeatureClassInOrFeatureCodeInOrCountryCountryCodeIn(featureClasses: List<String>, featureCodes: List<String>, countryCodes: List<String>, pageable: Pageable): List<DestinationEntity> {
        return destinationRepository.findAllByFeatureClassInOrFeatureCodeInOrCountryCountryCodeIn(featureClasses, featureCodes, countryCodes, pageable)
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

    fun searchDestinations(keyword: String, pageNumber: Int, pageSize: Int): ResponseEntity<List<DestinationSearchResult>> {
        val result = (findDestinationsByAsciiNameStartingWith(keyword) +
                findDestinationsByCountryCodeStartingWith(keyword) +
                findDestinationsByCountryNameStartingWith(keyword))
        val startIndex = pageNumber * pageSize
        var endIndex = (pageNumber + 1) * pageSize - 1
        if (endIndex >= result.size) {
            endIndex = result.size - 1
        }
        if (startIndex <= result.size) {
            return ResponseEntity.ok().body(
                result
                    .subList(startIndex, endIndex)
                    .map{
                        destinationSearchResultFromProjection(it)
                    }
            )
        }
        else {
            return ResponseEntity.ok().body(
                listOf()
            )
        }
    }

    fun recommendDestinationsToUser(username: String, pageNumber: Int, pageSize: Int): ResponseEntity<List<Destination>> {
        val featureInterests = interestsService
            .findAllByEntityInAndUserUsernameOrderByValueDesc(
                listOf (InterestKeyEntityType.FEATURE_CLASS, InterestKeyEntityType.FEATURE_CODE, InterestKeyEntityType.COUNTRY),
                username)
            .map {
                it.key
            }

        val page: Pageable = PageRequest.of(pageNumber, pageSize)
        val recommendations = findAllByFeatureClassInOrFeatureCodeInOrCountryCountryCodeIn(featureInterests, featureInterests, featureInterests, page)

        return ResponseEntity.ok().body(
            recommendations.map {
                destinationFromEntity(it)!!
            }
        )
    }
}