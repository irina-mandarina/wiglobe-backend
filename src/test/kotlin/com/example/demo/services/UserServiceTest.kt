package com.example.demo.services

import com.example.demo.repositories.UserRepository
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UserServiceTest {
    private val userRepository: UserRepository = mockk()
//    val userService = UserService(userRepository)

    @Test
    fun signUpTest() {
//        every { userRepository.findById(0) } returns User;
//
//        //when
//        val result = userService.findUserById(0);
//
//        //then
//        verify(exactly = 1) { userRepository.findByIdOrNull(0) };
//        assertEquals(user, result)
    }

    @Test
    fun test1() {
        assertEquals(1, 1, "0 != 1")
    }
}