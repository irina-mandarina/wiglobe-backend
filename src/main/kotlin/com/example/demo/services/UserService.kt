package com.example.demo.services

import com.example.demo.entities.UserDetailsEntity
import com.example.demo.entities.UserEntity
import com.example.demo.models.requestModels.GooglePayload
import com.example.demo.models.requestModels.LogInRequest
import com.example.demo.models.requestModels.SignUpRequest
import com.example.demo.models.responseModels.*
import com.example.demo.repositories.UserRepository
import com.example.demo.types.Gender
import com.example.demo.types.ProfilePrivacy
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.sql.Date
import java.util.*


@Service
 class UserService(private val userRepository: UserRepository, private val userDetailsService: UserDetailsService,
                   private val destinationService: DestinationService, private val jwtService: JWTService) {

    private val bCryptPasswordEncoder = BCryptPasswordEncoder()
    fun userNames(user: UserEntity): UserNames {
        return UserNames(
            user.username,
            user.firstName,
            user.lastName,
            user.userDetails!!.profilePicture,
            user.userDetails!!.gender
        )
    }

    fun userDetails(user: UserEntity): UserDetails {
        var residence: Destination? = null
        if (user.userDetails!!.residence !== null) {
            residence = destinationService.destinationFromEntity(user.userDetails!!.residence!!)
        }
        return UserDetails(
            user.username,
            user.firstName,
            user.lastName,
            user.userDetails!!.birthdate,
            user.userDetails!!.biography,
            user.userDetails!!.registrationTimestamp,
            user.userDetails!!.gender,
            residence,
            user.userDetails!!.privacy,
            user.userDetails!!.profilePicture,
            user.userDetails!!.backgroundPicture
        )
    }

    fun userWithUsernameExists(username: String): Boolean {
        return userRepository.findUserByUsername(username) != null
    }

    fun userWithEmailExists(email: String): Boolean {
        return (userRepository.findUserByEmail(email)) != null
    }

    fun findUserByEmail(email: String): UserEntity? {
        return userRepository.findUserByEmail(email)
    }

    fun findUserByUsername(username: String): UserEntity? {
        return userRepository.findUserByUsername(username)
    }

    fun logIn(logInRequest: LogInRequest): ResponseEntity<LogInResponse> {
        val user: UserEntity
        if (logInRequest.userIdentifier.contains("@")) {
            // the user is logging in with an email
            if (!userWithEmailExists(logInRequest.userIdentifier)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
            }
            user = findUserByEmail(logInRequest.userIdentifier)!!
        }
        else {
            if (!userWithUsernameExists(logInRequest.userIdentifier)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
            }
            user = findUserByUsername(logInRequest.userIdentifier)!!
        }
        return if (!bCryptPasswordEncoder.matches(logInRequest.password, user.password)) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                // wrong password
                .body(null)
        } else {
            ResponseEntity.ok()
                .body(
                    LogInResponse(
                        userDetails(
                            user
                        ),
                        jwtService.encode(user.username)
                    )
                )
        }
    }

    fun authenticateWithGoogle(token: String, googlePayload: GooglePayload): ResponseEntity<LogInResponse> {
        if (jwtService.googleJWTIsValid(token)) {
            val email = jwtService.getGoogleEmail(token)
            if (email != null) {
                val user = findUserByEmail(email)
                if (user != null) {
                    // log in
                    return ResponseEntity.ok()
                            .body(
                                    LogInResponse(
                                            userDetails(
                                                    user
                                            ),
                                            jwtService.encode(user.username)
                                    )
                            )
                }
                else {
                    // sign up
                    // create a username that is not taken:
                    var generatedUsername: String = googlePayload.email.substring(0, googlePayload.email.indexOf('@'))
                    while (userWithUsernameExists(generatedUsername)) {
                        generatedUsername += googlePayload.id
                            .toString()[generatedUsername.length - googlePayload.email.substring(0, googlePayload.email.indexOf('@')).length]
                    }
                    signUp(SignUpRequest(
                        generatedUsername,
                        googlePayload.email,
                        bcryptEncode(generatePassword()),
                        googlePayload.given_name.toString(),
                        googlePayload.family_name.toString(),
                        "",
                        null, Gender.OTHER,
                        googlePayload.picture
                    ))

                    val user = findUserByEmail(email)!!
                    return ResponseEntity.ok()
                            .body(
                                    LogInResponse(
                                            userDetails(
                                                    user
                                            ),
                                            jwtService.encode(user.username)
                                    )
                            )
                }
            }
            else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
            }

        }
        return ResponseEntity.ok().body(
            null
        )
    }

    private fun bcryptEncode(text: String): String {
        return bCryptPasswordEncoder.encode(text)
    }

    private fun generatePassword(): String {
        val leftLimit = 48 // numeral '0'
        val rightLimit = 122 // letter 'z'

        val targetStringLength = 15L
        val random = Random()

        val generatedString: String = random.ints(leftLimit, rightLimit + 1)
            .filter { i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97) }
            .limit(targetStringLength)
            .collect({ StringBuilder() }, java.lang.StringBuilder::appendCodePoint, java.lang.StringBuilder::append)
            .toString()

        return generatedString
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
        // create a user entity
        var user = UserEntity(signUpRequest)
        // create a userDetails entity
        var userDetails = UserDetailsEntity(signUpRequest)
        // save user details and assign them so an id is present
        userDetails = userDetailsService.save(userDetails)
        // assign the user details to the user
        user.userDetails = userDetails
        // save the user entity
        user = userRepository.save(user)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            SignUpResponse(
                emailTaken = false,
                usernameTaken = false,
                userDetails = userDetails(user)
            )
        )
    }

    fun deleteAccount(username: String): ResponseEntity<String> {
        val user = findUserByUsername(username)
        if (user != null) {
            userRepository.delete(user)
        }
        return ResponseEntity.ok()
            .body(null)
    }

    fun setBio(username: String, bio: String): ResponseEntity<UserDetails> {
        val userDetails = findUserByUsername(username)!!.userDetails!!
        userDetails.biography = bio
        userDetailsService.save(userDetails)

        return ResponseEntity.ok()
            .body(
                userDetails(
                    findUserByUsername(username)!!
                )
            )
    }

    fun getUserDetails(username: String, otherUserUsername: String): ResponseEntity<UserDetails> {
        val requestedDetailsOwner = findUserByUsername(otherUserUsername)!!
        if (requestedDetailsOwner.userDetails!!.privacy == ProfilePrivacy.PRIVATE) {
            if (! findUserByUsername(username)!!.isFollowing(requestedDetailsOwner)) {
                // can't
                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(
                    UserDetails(
                        requestedDetailsOwner.username,
                        requestedDetailsOwner.firstName,
                        requestedDetailsOwner.lastName,
                        null, null, null,
                        requestedDetailsOwner.userDetails!!.gender, null,
                        requestedDetailsOwner.userDetails!!.privacy,
                        requestedDetailsOwner.userDetails!!.profilePicture,
                        null
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
        val userDetails = findUserByUsername(username)!!.userDetails!!
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
        val userDetails = findUserByUsername(username)!!.userDetails!!
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
        val userDetails = findUserByUsername(username)!!.userDetails!!
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
        val userDetails = findUserByUsername(username)!!.userDetails!!
        userDetails.gender = gender
        userDetailsService.save(userDetails)
        return ResponseEntity.ok()
            .body(
                userDetails(
                    findUserByUsername(username)!!
                )
            )
    }

    fun setProfilePicture(username: String, profilePictureFilename: String): ResponseEntity<UserDetails> {
        val userDetails = findUserByUsername(username)!!.userDetails!!
        userDetails.profilePicture = profilePictureFilename
        userDetailsService.save(userDetails)
        return ResponseEntity.ok()
            .body(
                userDetails(
                    findUserByUsername(username)!!
                )
            )
    }

    fun setBackgroundPicture(username: String, backgroundPictureFilename: String): ResponseEntity<UserDetails> {
        val userDetails = findUserByUsername(username)!!.userDetails!!
        userDetails.backgroundPicture = backgroundPictureFilename
        userDetailsService.save(userDetails)
        return ResponseEntity.ok()
            .body(
                userDetails(
                    findUserByUsername(username)!!
                )
            )
    }

    fun searchUsers(keyword: String, pageNumber: Int, pageSize: Int): ResponseEntity<List<UserNames>> {
        val pageable = PageRequest.of(pageNumber, pageSize)
        return ResponseEntity.ok().body(
            userRepository.findAllByUsernameStartingWithOrFirstNameStartingWithOrLastNameStartingWith(keyword, keyword, keyword, pageable)
                .map { userNames(it) }
        )
    }
}