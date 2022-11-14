package com.example.demo.repositories

import com.example.demo.entities.Comment
import com.example.demo.entities.Journey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository: JpaRepository<Comment, Long> {
    fun findCommentsByJourney(journey: Journey): List<Comment>
}