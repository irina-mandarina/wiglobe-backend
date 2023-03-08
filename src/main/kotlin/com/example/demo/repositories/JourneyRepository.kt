package com.example.demo.repositories

import com.example.demo.entities.JourneyEntity
import com.example.demo.entities.UserEntity
import com.example.demo.types.Visibility
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface JourneyRepository: JpaRepository<JourneyEntity, Long>, PagingAndSortingRepository<JourneyEntity, Long> {
    fun findAllByUserUsernameOrderByPostedOnDesc(username: String): List<JourneyEntity>
    fun findAllByUserUsernameAndVisibilityIsIn(username: String, visibilities: List<Visibility>): List<JourneyEntity>
    fun findJourneyEntityById(id: Long): JourneyEntity?
    fun findAllByUserNotAndVisibility(user: UserEntity, visibility: Visibility): List<JourneyEntity>
    fun findAllByUserUsernameAndVisibility(username: String, visibility: Visibility): List<JourneyEntity>
    fun findAllByUserNotAndVisibilityNot(user: UserEntity, visibility: Visibility): List<JourneyEntity>
    fun findAllByDestinationIdAndVisibilityNot(destinationId: Long, visibility: Visibility): List<JourneyEntity>

    fun findAllByDescriptionContainingOrderByPostedOnDesc(keyword: String, pageable: Pageable): List<JourneyEntity>
    fun findAllByDescriptionContainingOrderByPostedOnDesc(keyword: String): List<JourneyEntity>
}