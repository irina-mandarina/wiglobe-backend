package com.example.demo.repositories

import com.example.demo.entities.Friendship
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FriendshipRepository: JpaRepository<Friendship, Long> {
}