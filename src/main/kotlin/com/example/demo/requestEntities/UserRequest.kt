package com.example.demo.requestEntities

import com.example.demo.entities.User
import java.sql.Date
import java.sql.Timestamp
import javax.persistence.Column

class UserRequest {
    var email: String? = ""
    var username: String? = ""
    var password: String? = ""
    var firstName: String = ""
    var lastName: String? = ""
    var registrationDate: Timestamp? = TODO()
    var biography: String? = ""
    var birthdate: Date = TODO()

    constructor(user: User) {
        this.email = user.email
        this.username = user.username
        this.password = user.password
        this.firstName = user.firstName
        this.lastName = user.lastName
        this.birthdate = user.birthdate
    }
}