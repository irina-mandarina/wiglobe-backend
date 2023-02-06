package com.example.demo.services

import com.example.demo.entities.*
import com.example.demo.models.Notification
import com.example.demo.repositories.NotificationRepository
import com.example.demo.types.NotificationObjectEntityType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.LocalDateTime

@Service
class NotificationService(private val notificationRepository: NotificationRepository) {
    fun notificationFromEntity(notification: NotificationEntity): Notification {
        return Notification(
            notification.receiver.username, notification.subject.username,
            notification.timestamp,
            notification.objectType, notification.objectId,
            notification.prepositionObjectType, notification.prepositionObjectId,
            notification.content)
    }
    fun notifyForComment(obj: CommentEntity, content: String) {
        val notification = NotificationEntity()
        notification.receiver = obj.journey.user
        notification.subject = obj.user
        notification.timestamp = Timestamp.valueOf(LocalDateTime.now())
        notification.objectType = NotificationObjectEntityType.COMMENT
        notification.objectId = obj.id
        notification.prepositionObjectType = NotificationObjectEntityType.JOURNEY
        notification.prepositionObjectId = obj.journey.id
        notification.content = content
        notificationRepository.save(notification)
    }

    fun notifyForFollow(obj: FollowEntity, content: String) {
        val notification = NotificationEntity()
        notification.receiver = obj.followed
        notification.subject = obj.follower
        notification.timestamp = Timestamp.valueOf(LocalDateTime.now())
        notification.objectType = NotificationObjectEntityType.FOLLOW
        notification.objectId = obj.id
        notification.prepositionObjectType = null
        notification.prepositionObjectId = null
        notification.content = content
        notificationRepository.save(notification)
    }

    fun notifyForFollowRequestResponse(obj: FollowRequestEntity, content: String) {
        val notification = NotificationEntity()
        notification.receiver = obj.requester
        notification.subject = obj.receiver
        notification.timestamp = Timestamp.valueOf(LocalDateTime.now())
        notification.objectType = NotificationObjectEntityType.FOLLOW_REQUEST
        notification.objectId = obj.id
        notification.prepositionObjectType = null
        notification.prepositionObjectId = null
        notification.content = content
        notificationRepository.save(notification)
    }

    fun notifyForFollowRequest(obj: FollowRequestEntity, content: String) {
        val notification = NotificationEntity()
        notification.receiver = obj.receiver
        notification.subject = obj.requester
        notification.timestamp = Timestamp.valueOf(LocalDateTime.now())
        notification.objectType = NotificationObjectEntityType.FOLLOW_REQUEST
        notification.objectId = obj.id
        notification.prepositionObjectType = null
        notification.prepositionObjectId = null
        notification.content = content
        notificationRepository.save(notification)
    }

    fun getNotifications(username: String): ResponseEntity<List<Notification>> {
        return ResponseEntity.ok().body(
            findAllByReceiverUsername(username).map{
                notificationFromEntity(it)
            }
        )
    }

    fun deleteNotificationForFollowRequest(obj: FollowRequestEntity) {
        notificationRepository.delete(
            findByReceiverUsernameAndSubjectUsernameAndObjectTypeAndObjectId(
                obj.receiver.username, obj.requester.username, NotificationObjectEntityType.FOLLOW_REQUEST, obj.id
            )
        )
    }

    fun deleteNotificationForComment(obj: CommentEntity) {
        notificationRepository.delete(
            findByReceiverUsernameAndSubjectUsernameAndObjectTypeAndObjectId(
                obj.journey.user.username, obj.user.username, NotificationObjectEntityType.COMMENT, obj.id
            )
        )
    }

    fun deleteNotificationForFollow(obj: FollowEntity) {
        notificationRepository.delete(
            findByReceiverUsernameAndSubjectUsernameAndObjectTypeAndObjectId(
                obj.followed.username, obj.follower.username, NotificationObjectEntityType.FOLLOW, obj.id
            )
        )
    }

    fun findByReceiverUsernameAndSubjectUsernameAndObjectTypeAndObjectId
                (receiver: String, requester: String, objType: NotificationObjectEntityType, objId: Long): NotificationEntity {
        return notificationRepository
            .findByReceiverUsernameAndSubjectUsernameAndObjectTypeAndObjectId(receiver, requester, objType, objId)
    }

    fun findAllByReceiverUsername(receiver: String): List<NotificationEntity> {
        return notificationRepository.findAllByReceiverUsername(receiver)
    }

}