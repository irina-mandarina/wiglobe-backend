package com.example.demo.controllers

import com.example.demo.models.Notification
import com.example.demo.services.NotificationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("http://localhost:3000")
class NotificationController(private val notificationService: NotificationService) {

    // not tested
    @GetMapping("/notifications")
    fun getNotifications(@RequestAttribute username: String): ResponseEntity<List<Notification>> {
        return notificationService.getNotifications(username)
    }
}