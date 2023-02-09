package com.example.demo.repositories

import com.example.demo.entities.DestinationEntity
import com.example.demo.models.projections.DestinationSearchProjection
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DestinationRepository: JpaRepository<DestinationEntity, Long> {
    fun findDestinationById(id: Long?): DestinationEntity?
    fun findAllByFeatureClassInOrFeatureCodeInOrCountryCountryCodeIn(featureClasses: List<String>,
                                               featureCodes: List<String>, countryCodes: List<String>, pageable: Pageable): List<DestinationEntity>
    fun findDestinationsByAsciiNameStartingWith(keyword: String): List<DestinationSearchProjection>
    fun findDestinationsByCountryCountryNameStartingWith(keyword: String): List<DestinationSearchProjection>
    fun findDestinationsByCountryCountryCodeStartingWith(keyword: String): List<DestinationSearchProjection>
    fun findAllByFeatureClass(featureClass: String): List<DestinationEntity>
    fun findAllByFeatureCode(featureCode: String): List<DestinationEntity>
}