package com.example.demo.services

import com.example.demo.entities.CountryEntity
import com.example.demo.models.responseModels.Country
import com.example.demo.repositories.CountryRepository
import org.springframework.stereotype.Service

@Service
class CountryService(private val countryRepository: CountryRepository) {

    fun countryFromEntity(country: CountryEntity?): Country? {
        if (country == null) {
            return null
        }
        return Country (
            country.countryCode,
            country.countryName,
            country.capital,
            country.area,
            country.continent
        )
    }
}