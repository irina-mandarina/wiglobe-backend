package com.example.demo.repositories

import com.example.demo.entities.FollowRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FollowRequestRepository: JpaRepository<FollowRequest, Long> {
    fun findByReceiverUsernameAndAndRequesterUsername(receiver: String, requesterUsername: String): FollowRequest?
    fun findAllByReceiverUsername(receiverUsername: String): List<FollowRequest>


}