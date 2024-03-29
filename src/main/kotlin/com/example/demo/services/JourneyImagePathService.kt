package com.example.demo.services

import com.example.demo.entities.JourneyEntity
import com.example.demo.entities.JourneyImageEntity
import com.example.demo.repositories.JourneyImageRepository
import org.springframework.stereotype.Service


@Service
class JourneyImagePathService(private val journeyImageRepository: JourneyImageRepository) {

    fun findAllByJourney(journeyEntity: JourneyEntity): List<JourneyImageEntity> {
        return journeyImageRepository.findAllByJourney(journeyEntity)
    }

    fun save(images: List<String>?, journeyEntity: JourneyEntity) {
        if (images != null) {
            for (path in images) {
                val img = JourneyImageEntity(journeyEntity, path)
                journeyImageRepository.save(img)
            }
        }
    }

}