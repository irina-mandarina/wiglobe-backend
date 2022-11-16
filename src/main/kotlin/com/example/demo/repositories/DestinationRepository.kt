package com.example.demo.repositories

import com.example.demo.entities.Destination
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DestinationRepository: JpaRepository<Destination, Long> {
    fun findDestinationById(id: Long): Destination?
    fun findDestinationByName(name: String): Destination?
}