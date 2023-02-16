package com.example.demo.repositories

import com.example.demo.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<UserEntity, Long> {
    fun findUserById(id: Long): UserEntity?
    fun findUserByUsername(username: String): UserEntity?
    fun findUserByEmail(email: String): UserEntity?
}