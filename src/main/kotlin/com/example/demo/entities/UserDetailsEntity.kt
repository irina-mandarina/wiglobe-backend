package com.example.demo.entities

import com.example.demo.models.requestModels.SignUpRequest
import com.example.demo.types.Gender
import com.example.demo.types.ProfilePrivacy
import kotlinx.serialization.Serializable
import lombok.NoArgsConstructor
import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*
import kotlin.math.sin

@Entity
@Table(name = "user_details")
class UserDetailsEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = -1

    @Column(name = "biography")
    var biography: String = ""

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    var gender: Gender = Gender.OTHER

    @Column(name = "registration_timestamp")
    var registrationTimestamp: Timestamp = Timestamp.valueOf(LocalDateTime.now())

    @Column(name = "birthdate")
    var birthdate: Date? = Date.valueOf(LocalDate.now())

    @Column(name = "profile_privacy")
    @Enumerated(EnumType.STRING)
    var privacy: ProfilePrivacy = ProfilePrivacy.PUBLIC

    @JoinColumn(name = "residence")
    @OneToOne
    var residence: DestinationEntity? = null
    constructor(signUpRequest: SignUpRequest) : this() {
        this.biography = signUpRequest.biography
        this.birthdate = signUpRequest.birthdate
        if (signUpRequest.gender == null) {
            this.gender = Gender.OTHER
        }
        else {
            this.gender = signUpRequest.gender
        }
        this.registrationTimestamp = Timestamp.valueOf(LocalDateTime.now())
        this.privacy = ProfilePrivacy.PUBLIC
    }
}