package com.example.demo.repositories

import com.example.demo.entities.CountryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CountryRepository: JpaRepository<CountryEntity, Long> {
    fun findCountryByCountryCode(countryCode: String): CountryEntity
}