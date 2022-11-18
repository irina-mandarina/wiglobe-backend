package com.example.demo

import com.example.demo.repositories.UserRepository
import com.example.demo.services.UserService
import io.mockk.mockk
import org.junit.jupiter.api.Test

class UserServiceTest {
    val userRepository: UserRepository = mockk();
    val userService = UserService(userRepository)
    @Test
    fun signUpTest() {

    }
}