package com.example.demo.repositories

import com.example.demo.entities.FeatureCode
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FeatureCodesRepository: JpaRepository<FeatureCode, Long> {
    fun findFeatureCodeByCode(code: String): FeatureCode
    fun findFirstByFeatureClass(featureClass: String): FeatureCode
}