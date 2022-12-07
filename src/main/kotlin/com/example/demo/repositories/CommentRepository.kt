package com.example.demo.repositories

import com.example.demo.entities.CommentEntity
import com.example.demo.entities.JourneyEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository: JpaRepository<CommentEntity, Long> {
    fun findCommentsByJourney(journey: JourneyEntity): List<CommentEntity>
    fun findCommentById(id: Long): CommentEntity?
}