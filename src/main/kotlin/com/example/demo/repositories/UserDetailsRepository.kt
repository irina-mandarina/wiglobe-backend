package com.example.demo.repositories

import com.example.demo.entities.UserDetailsEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDetailsRepository: JpaRepository<UserDetailsEntity, Long> {
//    fun findByUserUsername(username: String): UserDetailsEntity

}