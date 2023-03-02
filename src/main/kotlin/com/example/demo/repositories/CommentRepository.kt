package com.example.demo.repositories

import com.example.demo.entities.CommentEntity
import com.example.demo.entities.JourneyEntity
import com.example.demo.entities.UserEntity
import com.example.demo.types.EntityStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository: JpaRepository<CommentEntity, Long> {
    fun findCommentsByJourneyAndStatus(journey: JourneyEntity, status: EntityStatus = EntityStatus.POSTED): List<CommentEntity>
    fun findCommentById(id: Long): CommentEntity?
    fun findAllByUser(user: UserEntity): List<CommentEntity>
    fun findAllByUserAndIdGreaterThanAndStatus(user: UserEntity, id: Long, status: EntityStatus = EntityStatus.POSTED): List<CommentEntity>
}