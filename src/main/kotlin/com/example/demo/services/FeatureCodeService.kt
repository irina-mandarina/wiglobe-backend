package com.example.demo.services

import com.example.demo.entities.FeatureCode
import com.example.demo.repositories.FeatureCodesRepository
import org.springframework.stereotype.Service

@Service
class FeatureCodeService(private val featureCodesRepository: FeatureCodesRepository) {
    fun findFeatureCodeByCode(code: String): FeatureCode? {
        return featureCodesRepository.findFeatureCodeByCode(code)
    }

    fun findFirstByFeatureClass(code: String): FeatureCode? {
        return featureCodesRepository.findFirstByFeatureClass(code);
    }

    fun findFeatureCodeMeaning(code: String): String? {
        return findFeatureCodeByCode(code)?.meaning
    }

    fun findFeatureClassMeaning(featureClass: String): String? {
        return findFirstByFeatureClass(featureClass)?.featureClassMeaning
    }
}