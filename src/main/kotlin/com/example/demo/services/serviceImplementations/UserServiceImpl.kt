package com.example.demo.services.serviceImplementations

import com.example.demo.requestEntities.LogInRequest
import com.example.demo.requestEntities.RegistrationRequest
import com.example.demo.services.UserService
import lombok.NoArgsConstructor
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
@NoArgsConstructor
class UserServiceImpl: UserService {
    override fun logIn(logInRequest: LogInRequest): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun register(registrationRequest: RegistrationRequest): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun logOut(username: String): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun deleteAccount(username: String): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun addBio(username: String, bio: String): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun editBio(username: String, bio: String): ResponseEntity<String> {
        TODO("Not yet implemented")
    }
}