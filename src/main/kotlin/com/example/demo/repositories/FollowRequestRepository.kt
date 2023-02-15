package com.example.demo.repositories

import com.example.demo.entities.FollowRequestEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FollowRequestRepository: JpaRepository<FollowRequestEntity, Long> {
    fun findByReceiverUsernameAndRequesterUsername(receiver: String, requesterUsername: String): FollowRequestEntity?
    fun findAllByReceiverUsername(receiverUsername: String): List<FollowRequestEntity>
    fun findAllByRequesterUsername(requesterUsername: String): List<FollowRequestEntity>
}