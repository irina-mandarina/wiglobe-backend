package com.example.demo.services

import com.example.demo.entities.UserEntity
import com.example.demo.repositories.UserRepository
import com.example.demo.models.requestModels.LogInRequest
import com.example.demo.models.requestModels.SignUpRequest
import com.example.demo.models.responseModels.UserDetails
import com.example.demo.models.responseModels.UserNames
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
 class UserService(private val userRepository: UserRepository) {

    fun userNames(user: UserEntity): UserNames {
        return UserNames(
            user.username,
            user.firstName,
            user.lastName
        )
    }

    fun userDetails(user: UserEntity): UserDetails {
        return UserDetails(
            user.username,
            user.firstName,
            user.lastName,
            user.birthdate,
            user.biography,
            user.registrationDate,
            user.gender
        )
    }

    fun userWithUsernameExists(username: String): Boolean {
        return (userRepository.findUserByUsername(username) != null)
    }

    fun userWithEmailExists(email: String): Boolean {
        return ((userRepository.findUserByEmail(email)) != null)
    }

    fun findUserById(id: Long): UserEntity? {
        return userRepository.findUserById(id)
    }

    fun findUserByUsername(username: String): UserEntity? {
        return userRepository.findUserByUsername(username)
    }

    fun logIn(logInRequest: LogInRequest): ResponseEntity<UserDetails> {
        if (userWithUsernameExists(logInRequest.username!!)) {
            val user = findUserByUsername(logInRequest.username)!!
            return if (user.password != logInRequest.password) {
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(
                    "message", "Wrong password")
                    .body(null)
            } else {
                ResponseEntity.ok().header(
                    "message", "Successfully logged in")
                    .body(
                        userDetails(
                            user
                        )
                    )
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
            "message", "Username does not exist")
            .body(null)
    }

    fun signUp(signUpRequest: SignUpRequest): ResponseEntity<UserDetails> {
        if (userWithUsernameExists(signUpRequest.username)) {
            return ResponseEntity.badRequest().header(
                "message", "User with username: " + signUpRequest.username + " already exists")
                .body(null)
        }

        if (userWithEmailExists(signUpRequest.email)) {
            return ResponseEntity.badRequest().header(
                "message", "User with email: " + signUpRequest.email + " already exists")
                .body(null)
        }

        val user = UserEntity(signUpRequest)

        userRepository.save(user)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            userDetails(
                user
            )
        )
    }

    fun logOut(username: String): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    fun deleteAccount(username: String): ResponseEntity<String> {
        return if (!userWithUsernameExists(username)) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message", "User $username does not exist")
                .body(null)
        } else {
            val user = findUserByUsername(username)
            if (user != null) {
                userRepository.delete(user)
            }
            ResponseEntity.ok().header(
                "message", "Deleted account with username: $username")
                .body(null)
        }

    }

    fun setBio(username: String, bio: String): ResponseEntity<String> {
        return if (!userWithUsernameExists(username)) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message", "User with username: " + username + "does not exist")
                .body(null)
        } else {
            val user = findUserByUsername(username)
            if (user != null) {
                user.biography = bio
                userRepository.save(user)
            }
            ResponseEntity.ok().header(
                "message", "Changed biography for account with username: $username to: $bio")
                .body(bio)
        }
    }

    fun getUserDetails(username: String): ResponseEntity<UserDetails> {
        if (!userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .header(
                "message", "User with username: " + username + "does not exist")
                .body(null)
        }

        val user = findUserByUsername(username)!!
        return ResponseEntity.ok().body(
            userDetails(
                user
            )
        )
    }
}