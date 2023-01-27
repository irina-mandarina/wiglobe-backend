package com.example.demo.repositories

import com.example.demo.entities.CommentEntity
import com.example.demo.entities.JourneyEntity
import com.example.demo.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository: JpaRepository<CommentEntity, Long> {
    fun findCommentsByJourney(journey: JourneyEntity): List<CommentEntity>
    fun findCommentById(id: Long): CommentEntity?
    fun findAllByUser(user: UserEntity): List<CommentEntity>
    fun findAllByUserAndIdGreaterThan(user: UserEntity, id: Long): List<CommentEntity>
}