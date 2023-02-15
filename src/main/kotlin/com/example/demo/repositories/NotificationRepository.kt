package com.example.demo.repositories

import com.example.demo.entities.NotificationEntity
import com.example.demo.types.NotificationObjectEntityType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepository: JpaRepository<NotificationEntity, Long> {
    fun findByReceiverUsernameAndSubjectUsernameAndObjectTypeAndObjectId(
        receiver: String, requester: String, objType: NotificationObjectEntityType, objId: Long): NotificationEntity

    fun findAllByReceiverUsernameOrderByTimestampDesc(receiver: String): List<NotificationEntity>
}