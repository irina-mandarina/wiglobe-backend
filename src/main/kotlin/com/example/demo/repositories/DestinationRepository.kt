package com.example.demo.repositories

import com.example.demo.entities.DestinationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DestinationRepository: JpaRepository<DestinationEntity, Long> {
    fun findDestinationById(id: Long): DestinationEntity?
//    fun findDestinationByNameLikeOrCountryCodeCoun(name: String): DestinationEntity?
//    fun findDestinationEntitiesByNameLikeOrCountryContryNameLike()
    fun findAllByFeatureClass(featureClass: String): List<DestinationEntity>
    fun findAllByFeatureCode(featureCode: String): List<DestinationEntity>
}