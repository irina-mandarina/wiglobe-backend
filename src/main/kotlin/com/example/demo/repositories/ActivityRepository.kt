package com.example.demo.repositories

import com.example.demo.entities.Activity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ActivityRepository: JpaRepository<Activity, Long> {
}