package com.example.demo.services

import com.example.demo.entities.UserDetailsEntity
import com.example.demo.entities.UserEntity
import com.example.demo.models.requestModels.SignUpRequest
import com.example.demo.repositories.UserDetailsRepository
import org.springframework.stereotype.Service

@Service
class UserDetailsService(private val userDetailsRepository: UserDetailsRepository) {
    public fun findByUserUsername(username: String): UserDetailsEntity {
        return userDetailsRepository.findByUserUsername(username)
    }

    public fun save(userDetailsEntity: UserDetailsEntity): UserDetailsEntity {
        return save(userDetailsEntity)
    }

    public fun save(signUpRequest: SignUpRequest): UserDetailsEntity {
        return save (
            UserDetailsEntity(signUpRequest)
        )
    }
}