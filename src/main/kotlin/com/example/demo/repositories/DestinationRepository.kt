package com.example.demo.repositories

import com.example.demo.entities.DestinationEntity
import com.example.demo.models.projections.DestinationSearchProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DestinationRepository: JpaRepository<DestinationEntity, Long> {
    fun findDestinationById(id: Long?): DestinationEntity?
    fun findDestinationEntitiesByNameLikeOrCountryCountryNameLikeOrCountryCountryCodeLikeOrAsciiNameLike(
        name: String,
        countryCountryName: String,
        countryCountryCode: String,
        asciiName: String
    ): List<DestinationEntity>

//    @Query("SELECT d.id as destinationId, d.featureCode as featureCode, d.name as name, d.country.countryName as country FROM DestinationEntity as d " +
//            "WHERE d.asciiName like ':keyword%'")
    fun findDestinationsByAsciiNameStartingWith(keyword: String): List<DestinationSearchProjection>

//    @Query("SELECT d.id as destinationId, d.featureCode as featureCode, d.name as name, d.country.countryName as country FROM DestinationEntity as d " +
//            "WHERE d.country.countryName like ':keyword%'")
    fun findDestinationsByCountryCountryNameStartingWith(keyword: String): List<DestinationSearchProjection>

//    @Query("SELECT d.id as destinationId, d.featureCode as featureCode, d.name as name, d.country.countryName as country FROM DestinationEntity as d " +
//            "WHERE d.country.countryCode like ':keyword%'")
    fun findDestinationsByCountryCountryCodeStartingWith(keyword: String): List<DestinationSearchProjection>

    fun findAllByFeatureClass(featureClass: String): List<DestinationEntity>
    fun findAllByFeatureCode(featureCode: String): List<DestinationEntity>
}