package com.example.demo.services

import com.example.demo.entities.Destination

interface DestinationService {
    fun destinationWithIdExists(id: Long): Boolean
    fun findDestinationById(id: Long): Destination?
    fun findDestinationByName(name: String): Destination?
}