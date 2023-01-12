package com.example.demo.repositories

import com.example.demo.entities.DestinationEntity
import com.example.demo.models.responseModels.DestinationSearchResult
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface DestinationRepository: JpaRepository<DestinationEntity, Long> {
    fun findDestinationById(id: Long): DestinationEntity?
    fun findDestinationEntitiesByNameLikeOrCountryCountryNameLikeOrCountryCountryCodeLikeOrAsciiNameLike(
        name: String,
        countryCountryName: String,
        countryCountryCode: String,
        asciiName: String
    ): List<DestinationEntity>


//    select DISTINCT name, fc.feature_class_meaning, c.country_name as country from geonames as g
//    left join countries as c on g.country_code = c.country_code
//    left join feature_codes fc on g.feature_class = fc.feature_class
//    where name like '%karlovo'
//    or country_name like '%karlovo%'
//    or asciiname like '%karlovo%'
//    or g.country_code like '%karlovo%'
    @Query("select DISTINCT g.id as destinationId, g.name as name, g.featureCode, g.country.countryName as country from DestinationEntity as g\n" +
//            "left join FeatureCode on FeatureCode.code = g.featureCode\n" +
            "where g.name like '%:keyword%'\n" +
            "or g.country.countryName like '%:keyword%'\n" +
            "or g.asciiName like '%:keyword%'\n" +
            "or g.country.countryCode like '%:keyword%'")
    fun findDestinationIdAndNameAndCountryByKeyword(keyword: String): List<DestinationSearchResult>
    fun findAllByFeatureClass(featureClass: String): List<DestinationEntity>
    fun findAllByFeatureCode(featureCode: String): List<DestinationEntity>
}
/*

    @Query("select DISTINCT g.name as name, fc.featureClassMeaning as feature_class, c.countryName as country from DestinationEntity as g\n" +
            "left join CountryEntity as c on g.country.countryCode = c.countryCode\n" +
            "left join FeatureCode fc on g.featureClass = fc.featureClass\n" +
            "where g.name like '%:keyword%'\n" +
            "or g.country.countryName like '%:keyword%'\n" +
            "or g.asciiName like '%:keyword%'\n" +
            "or g.country.countryCode like '%:keyword%'")
    fun findDestinationIdAndNameAndCountryByKeyword(keyword: String): List<DestinationSearchResult>
 */