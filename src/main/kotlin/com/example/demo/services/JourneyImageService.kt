package com.example.demo.services

import com.example.demo.entities.JourneyEntity
import com.example.demo.entities.JourneyImageEntity
import com.example.demo.repositories.JourneyImageRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.*


@Service
class JourneyImageService(private val journeyImageRepository: JourneyImageRepository) {
    @Value("\\\${imagefolder}")
    lateinit var imageFolderPath: String

    fun findAllByJourney(journeyEntity: JourneyEntity): List<JourneyImageEntity> {
        return journeyImageRepository.findAllByJourney(journeyEntity)
    }

    fun save(images: List<MultipartFile>, journeyEntity: JourneyEntity) {
        val uploadPath: Path = Paths.get(imageFolderPath)

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath)
        }

        for (image in images) {
            // temporary filename
            var filename = "journey${journeyEntity.id}"

            var imageEntity = JourneyImageEntity(journeyEntity, filename)

            imageEntity = journeyImageRepository.save(imageEntity)

            filename = "journey${journeyEntity.id}${imageEntity.id}"
            imageEntity.filepath = filename

            // save with an updated filename
            journeyImageRepository.save(imageEntity)

            try {
                image.inputStream.use { inputStream ->
                    val filePath: Path = uploadPath.resolve(filename)
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING)
                }
            } catch (ioe: IOException) {
                println("ignored ex")
            }
        }
    }

    fun save(image: MultipartFile, journeyEntity: JourneyEntity): JourneyImageEntity? {
        val uploadPath: Path = Paths.get(imageFolderPath)

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath)
        }

        // temporary filename
        var filename = "journey${journeyEntity.id}"

        var imageEntity = JourneyImageEntity(journeyEntity, filename)

        imageEntity = journeyImageRepository.save(imageEntity)

        filename = "journey${journeyEntity.id}${imageEntity.id}"
        imageEntity.filepath = filename

        // save with an updated filename
        journeyImageRepository.save(imageEntity)

        try {
            image.inputStream.use { inputStream ->
                val filePath: Path = uploadPath.resolve(filename)
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING)
            }
        } catch (ioe: IOException) {
            println("ignored ex")
        }

        return imageEntity
    }

}