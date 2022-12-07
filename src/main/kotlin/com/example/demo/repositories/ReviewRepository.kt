package com.example.demo.repositories

import com.example.demo.entities.DestinationEntity
import com.example.demo.entities.ReviewEntity
import com.example.demo.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository: JpaRepository<ReviewEntity, Long> {
    fun findReviewById(id: Long): ReviewEntity?

    fun findReviewsByUser(user: UserEntity): List<ReviewEntity>
    fun findReviewsByDestination(destination: DestinationEntity): List<ReviewEntity>
}