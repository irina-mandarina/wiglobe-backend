package com.example.demo.services.serviceImplementations

import com.example.demo.entities.User
import com.example.demo.repositories.UserRepository
import com.example.demo.requestEntities.UserRequest
import com.example.demo.services.UserService
import lombok.NoArgsConstructor
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
@NoArgsConstructor
abstract class UserServiceImpl(val userRepository: UserRepository): UserService {
    override fun userWithUsernameExists(username: String): Boolean {
        return (userRepository.findUserByUsername(username) == null)
    }

    override fun userWithEmailExists(email: String): Boolean {
        return ((userRepository.findUserByEmail(email)) == null)
    }

    override fun findUserById(id: Long): User? {
        return userRepository.findUserById(id)
    }

    override fun findUserByUsername(username: String): User? {
        return userRepository.findUserByUsername(username)
    }

    override fun logIn(userRequest: UserRequest): ResponseEntity<String> {
        if ((userRequest.username.isNullOrBlank() && userRequest.email.isNullOrBlank()) ||
            userRequest.password.isNullOrBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("")
        }

        if (!userWithUsernameExists(userRequest.username!!)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username does not exist")
        }

        return if (findUserByUsername(userRequest.username!!)!!.password != userRequest.password) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong password")
        } else {
            ResponseEntity.ok().body("Successfully logged in")
        }

        return ResponseEntity.badRequest().body("Log in request failed")
    }

    override fun register(userRequest: UserRequest): ResponseEntity<String> {
        if (userRequest.username.isNullOrBlank() || userRequest.email.isNullOrBlank() ||
            userRequest.password.isNullOrBlank() || userRequest.firstName.isNullOrBlank()) {
            return ResponseEntity.badRequest().body("Missing request details")
        }

        if (userWithUsernameExists(userRequest.username!!)) {
            return ResponseEntity.badRequest().body("User with username: " + userRequest.username + "already exists")
        }

        if (userWithEmailExists(userRequest.email!!)) {
            return ResponseEntity.badRequest().body("User with email: " + userRequest.email + "already exists")
        }

        val user: User = User(userRequest)

        userRepository.save(user)

        return ResponseEntity.status(HttpStatus.CREATED).body("Registered user with username: " + user.username)
    }

    override fun logOut(username: String): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun deleteAccount(username: String): ResponseEntity<String> {
        if (username.isNullOrBlank()) {
            return ResponseEntity.badRequest().body("Missing request parameter: username")
        }

        return if (!userWithUsernameExists(username)) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with username: " + username + "does not exist")
        } else {
            val user = findUserByUsername(username)
            if (user != null) {
                userRepository.delete(user)
            }
            ResponseEntity.ok().body("Deleted account with username: $username")
        }

    }

    override fun setBio(username: String, bio: String): ResponseEntity<String> {
        if (username.isNullOrBlank()) {
            return ResponseEntity.badRequest().body("Missing request parameter: username")
        }

        return if (!userWithUsernameExists(username)) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with username: " + username + "does not exist")
        } else {
            var user = findUserByUsername(username)
            if (user != null) {
                user.biography = bio
                userRepository.save(user)
            }
            ResponseEntity.ok().body("Changed biography for account with username: $username to: $bio")
        }
    }

    override fun getUserDetails(username: String): ResponseEntity<String> {
        if (username.isNullOrBlank()) {
            return ResponseEntity.badRequest().body("Missing request parameter: username")
        }

        if (!userWithUsernameExists(username)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("User with username: " + username + "does not exist")
        }

        return ResponseEntity.ok().body(UserRequest(findUserByUsername(username)!!).toString())
    }
}