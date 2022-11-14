package com.example.demo.repositories

import com.example.demo.entities.FriendRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FriendRequestRepository: JpaRepository<FriendRequest, Long> {

}