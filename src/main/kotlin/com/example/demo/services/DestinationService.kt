package com.example.demo.services

import com.example.demo.entities.DestinationEntity
import com.example.demo.models.responseModels.Destination
import com.example.demo.repositories.DestinationRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.io.FileReader
import java.nio.charset.StandardCharsets

@Service
class DestinationService(private val destinationRepository: DestinationRepository) {
    val featureClasses = mapOf<String, String>("A" to "country, state, region",
        "H" to "stream, lake", "L" to "parks, area", "P" to "city, village",
        "R" to "rail, railroad", "S" to "spot, building, farm", "T" to "mountain, hill, rock",
        "U" to "undersea", "V" to "forest, health")


    fun destinationWithIdExists(id: Long): Boolean {
        return (findDestinationById(id) != null)
    }

    fun findDestinationById(id: Long): DestinationEntity? {
        return destinationRepository.findDestinationById(id)
    }

    fun findDestinationByName(name: String): DestinationEntity? {
        return destinationRepository.findDestinationByName(name)
    }

    fun readFeatureCodeCsvAndReplaceFeatureClasses(): ResponseEntity<String> {
        featureClasses.forEach { (key, value) ->
            destinationRepository.findAllByFeatureClass(key).forEach {
                    it -> it.featureClass = value} }

        return ResponseEntity.ok().body(null)
    }

    fun readFeatureCodeCsvAndReplaceFeatureCodes(): ResponseEntity<String> {
// to do
        return ResponseEntity.ok().body(null)
    }

    fun getDestinations(): ResponseEntity<List<Destination>> {
        return ResponseEntity.ok().body(
            destinationRepository.findAll().map {
                Destination(
                    it.latitude,
                    it.longitude,
                    it.name,
                    it.countryCode,
                    it.featureClass,
                    it.featureCode
                )
            }
        )
    }
}