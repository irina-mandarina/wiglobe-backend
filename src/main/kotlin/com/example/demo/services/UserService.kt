package com.example.demo.services

import com.example.demo.entities.User
import com.example.demo.repositories.UserRepository
import com.example.demo.requestEntities.LogInRequest
import com.example.demo.requestEntities.SignUpRequest
import com.example.demo.requestEntities.UserRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
 class UserService(val userRepository: UserRepository) {
    fun userWithUsernameExists(username: String): Boolean {
        return (userRepository.findUserByUsername(username) != null)
    }

    fun userWithEmailExists(email: String): Boolean {
        return ((userRepository.findUserByEmail(email)) != null)
    }

    fun findUserById(id: Long): User? {
        return userRepository.findUserById(id)
    }

    fun findUserByUsername(username: String): User? {
        return userRepository.findUserByUsername(username)
    }

    fun logIn(logInRequest: LogInRequest): ResponseEntity<String> {
        if (userWithUsernameExists(logInRequest.username!!)) {
            return if (findUserByUsername(logInRequest.username)!!.password != logInRequest.password) {
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong password")
            } else {
                ResponseEntity.ok().body("Successfully logged in")
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username does not exist")
    }

    fun signUp(signUpRequest: SignUpRequest): ResponseEntity<String> {
        println("here")
        if (userWithUsernameExists(signUpRequest.username)) {
            return ResponseEntity.badRequest().body("User with username: " + signUpRequest.username + " already exists")
        }

        if (userWithEmailExists(signUpRequest.email)) {
            return ResponseEntity.badRequest().body("User with email: " + signUpRequest.email + " already exists")
        }

        val user = User(signUpRequest)

        userRepository.save(user)

        return ResponseEntity.status(HttpStatus.CREATED).body(UserRequest(findUserByUsername(user.username)!!).toString())
    }

    fun logOut(username: String): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    fun deleteAccount(username: String): ResponseEntity<String> {
        return if (!userWithUsernameExists(username)) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with username: " + username + " does not exist")
        } else {
            val user = findUserByUsername(username)
            if (user != null) {
                userRepository.delete(user)
            }
            ResponseEntity.ok().body("Deleted account with username: $username")
        }

    }

    fun setBio(username: String, bio: String): ResponseEntity<String> {
        return if (!userWithUsernameExists(username)) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with username: " + username + "does not exist")
        } else {
            val user = findUserByUsername(username)
            if (user != null) {
                user.biography = bio
                userRepository.save(user)
            }
            ResponseEntity.ok().body("Changed biography for account with username: $username to: $bio")
        }
    }

    fun getUserDetails(username: String): ResponseEntity<String> {
        if (!userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("User with username: " + username + "does not exist")
        }

        return ResponseEntity.ok().body(UserRequest(findUserByUsername(username)!!).toString())
    }
}