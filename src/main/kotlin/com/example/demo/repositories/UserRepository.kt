package com.example.demo.repositories

import com.example.demo.entities.UserEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<UserEntity, Long>, PagingAndSortingRepository<UserEntity, Long> {
    fun findUserById(id: Long): UserEntity?
    fun findUserByUsername(username: String): UserEntity?
    fun findUserByEmail(email: String): UserEntity?

    fun findAllByUsernameStartingWithOrFirstNameStartingWithOrLastNameStartingWith
                (username: String, firstName: String, lastName: String, pageable: Pageable): List<UserEntity>
}