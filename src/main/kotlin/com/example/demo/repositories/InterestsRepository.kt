package com.example.demo.repositories

import com.example.demo.entities.InterestEntity
import com.example.demo.entities.UserEntity
import com.example.demo.types.InterestKeyEntityType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InterestsRepository: JpaRepository<InterestEntity, Long> {
    fun findAllByUser(userEntity: UserEntity): List<InterestEntity>
    fun findByKeyAndEntityAndUser(key: String, keyType: InterestKeyEntityType, userEntity: UserEntity): InterestEntity?

    fun findFirstByUserOrderByLastCommentIdDesc(userEntity: UserEntity): InterestEntity?
}