package com.example.demo.services

import com.example.demo.entities.DestinationEntity
import com.example.demo.repositories.DestinationRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.io.FileReader
import java.nio.charset.StandardCharsets
import com.opencsv.CSVReader

@Service
class DestinationService(private val destinationRepository: DestinationRepository) {
    val featureClasses = mapOf<String, String>("A" to "country, state, region",
        "H" to "stream, lake", "L" to "parks, area", "P" to "city, village",
        "R" to "rail, railroad", "S" to "spot, building, farm", "T" to "mountain, hill, rock",
        "U" to "undersea", "V" to "forest, health")
    fun readCsvAndInsertDestinations(): ResponseEntity<String> {
        val fileName = "src/main/resources/static/data/geonames.csv"
        val fr = FileReader(fileName, StandardCharsets.UTF_8)

        fr.use {
            val reader = CSVReader(fr)

            reader.use { r ->

                var line = r.readNext()
                // skip the column names line
                line = r.readNext()

                while (line != null) {
                    val destination = DestinationEntity()
                    var columnIndex = 0

                    line.forEach {
                        when (columnIndex) {
                            1 -> {
                                destination.name = it
                                print("name: $it, ")
                            }
                            4 -> {
                                print("latitude: $it, ")
                                destination.latitude = it.toDouble()
                            }
                            5 -> {
                                print("longitude: $it, ")
                                destination.longitude = it.toDouble()
                            }
                            6 -> {
                                print("featureClass: $it, ")
                                destination.featureClass = it
                            }
                            7 -> {
                                print("featureCode: $it, ")
                                destination.featureCode = it
                            }
                            8 -> {
                                print("countryCode: $it, ")
                                destination.countryCode = it
                            }
                        }
                        destinationRepository.save(destination)
                        columnIndex++
                    }

                    println()


                    line = r.readNext()
                }
            }
        }

        return ResponseEntity.ok().body(null)
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
}