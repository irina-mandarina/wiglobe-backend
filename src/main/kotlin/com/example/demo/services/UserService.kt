package com.example.demo.services

import com.example.demo.entities.UserEntity
import com.example.demo.repositories.UserRepository
import com.example.demo.models.requestModels.LogInRequest
import com.example.demo.models.requestModels.SignUpRequest
import com.example.demo.models.responseModels.*
import com.example.demo.types.Gender
import com.example.demo.types.ProfilePrivacy
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.sql.Date

@Service
 class UserService(private val userRepository: UserRepository, private val userDetailsService: UserDetailsService,
                   private val destinationService: DestinationService, private val sessionService: SessionService) {

    fun userNames(user: UserEntity): UserNames {
        return UserNames(
            user.username,
            user.firstName,
            user.lastName
        )
    }

    fun userDetails(user: UserEntity): UserDetails {
//        val userDetails: UserDetailsEntity = userDetailsService.findByUserUsername(user.username)
        var residence: Destination? = null
        if (user.userDetails.residence !== null) {
            residence = destinationService.destinationFromEntity(user.userDetails.residence!!)
        }
        return UserDetails(
            user.username,
            user.firstName,
            user.lastName,
            user.userDetails.birthdate,
            user.userDetails.biography,
            user.userDetails.registrationTimestamp,
            user.userDetails.gender,
            residence,
            user.userDetails.privacy
        )
    }

    fun userWithUsernameExists(username: String): Boolean {
        return (userRepository.findUserByUsername(username) != null)
    }

    fun userWithEmailExists(email: String): Boolean {
        return ((userRepository.findUserByEmail(email)) != null)
    }

    fun findUserByUsername(username: String): UserEntity? {
        return userRepository.findUserByUsername(username)
    }

    fun logIn(logInRequest: LogInRequest): ResponseEntity<LogInResponse> {
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
                        LogInResponse(
                            userDetails(
                                user
                            ),
                            sessionService.issueJWT(logInRequest.username)
                        )
                    )
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
            "message", "Username does not exist")
            .body(null)
    }

    fun signUp(signUpRequest: SignUpRequest): ResponseEntity<SignUpResponse> {
        if (userWithUsernameExists(signUpRequest.username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                    SignUpResponse(
                        usernameTaken = true,
                        emailTaken = null,
                        userDetails = null
                    )
                )
        }

        if (userWithEmailExists(signUpRequest.email)) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                    SignUpResponse(
                        usernameTaken = false,
                        emailTaken = true,
                        userDetails = null
                    )
                )
        }

        var user = UserEntity(signUpRequest)

        user = userRepository.save(user)
        userDetailsService.save(signUpRequest)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            SignUpResponse(
                emailTaken = false,
                usernameTaken = false,
                userDetails = userDetails(user)
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

    fun setBio(username: String, bio: String): ResponseEntity<UserDetails> {
        return if (!userWithUsernameExists(username)) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "message", "User with username: " + username + "does not exist")
                .body(null)
        } else {
            val userDetails = userDetailsService.findByUserUsername(username)
            userDetails.biography = bio
            userDetailsService.save(userDetails)

            ResponseEntity.ok()
                .body(
                    userDetails(
                        findUserByUsername(username)!!
                    )
                )
        }
    }

    fun getUserDetails(username: String, otherUserUsername: String): ResponseEntity<UserDetails> {
        if (!userWithUsernameExists(username) || !userWithUsernameExists(otherUserUsername)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .header(
                "message", "User with username: " + username + "does not exist")
                .body(null)
        }
        val requestedDetailsOwner = findUserByUsername(otherUserUsername)!!
        if (requestedDetailsOwner.userDetails.privacy == ProfilePrivacy.PRIVATE) {
            if (! findUserByUsername(username)!!.isFollowing(requestedDetailsOwner)) {
                // can't
                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(
                    UserDetails(
                        requestedDetailsOwner.username,
                        requestedDetailsOwner.firstName,
                        requestedDetailsOwner.lastName,
                        null, null, null,
                        requestedDetailsOwner.userDetails.gender, null,
                        requestedDetailsOwner.userDetails.privacy
                    )
                )
            }
            // else ok
        }
        return ResponseEntity.ok().body(
            userDetails(
                requestedDetailsOwner
            )
        )
    }

    fun setResidence(username: String, destinationId: Long): ResponseEntity<UserDetails> {
        val userDetails = userDetailsService.findByUserUsername(username)
        userDetails.residence = destinationService.findDestinationById(destinationId)
        userDetailsService.save(userDetails)
        return ResponseEntity.ok()
            .body(
                userDetails(
                    findUserByUsername(username)!!
                )
            )
    }

    fun setProfilePrivacy(username: String, privacy: ProfilePrivacy): ResponseEntity<UserDetails> {
        val userDetails = userDetailsService.findByUserUsername(username)
        userDetails.privacy = privacy
        userDetailsService.save(userDetails)
        return ResponseEntity.ok()
            .body(
                userDetails(
                    findUserByUsername(username)!!
                )
            )
    }

    fun setBirthdate(username: String, birthdate: Date): ResponseEntity<UserDetails> {
        val userDetails = userDetailsService.findByUserUsername(username)
        userDetails.birthdate = birthdate
        userDetailsService.save(userDetails)
        return ResponseEntity.ok()
            .body(
                userDetails(
                    findUserByUsername(username)!!
                )
            )
    }

    fun setGender(username: String, gender: Gender): ResponseEntity<UserDetails> {
        val userDetails = userDetailsService.findByUserUsername(username)
        userDetails.gender = gender
        userDetailsService.save(userDetails)
        return ResponseEntity.ok()
            .body(
                userDetails(
                    findUserByUsername(username)!!
                )
            )
    }
}