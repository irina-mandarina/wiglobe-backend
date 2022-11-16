package com.example.demo.repositories

import com.example.demo.entities.Destination
import com.example.demo.entities.Journey
import com.example.demo.entities.Review
import com.example.demo.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository: JpaRepository<Review, Long> {
    fun findReviewById(id: Long): Review?

    fun findReviewsByUser(user: User): List<Review>
    fun findReviewsByDestination(destination: Destination): List<Review>
}