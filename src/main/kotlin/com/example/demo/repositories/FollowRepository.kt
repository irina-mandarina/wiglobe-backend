package com.example.demo.repositories

import com.example.demo.entities.FollowEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FollowRepository: JpaRepository<FollowEntity, Long> {
    fun findByFollowerIdAndAndFollowedId(followerId: Long, followedId: Long): FollowEntity?
}