package com.example.demo.entities

import com.example.demo.models.requestModels.SignUpRequest
import com.example.demo.serialization.TimestampSerializer
import com.example.demo.types.Gender
import kotlinx.serialization.Serializable
import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
class User() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    var id: Long = 0

    @Column(name = "email", nullable = false, unique = true)
    var email: String = ""

    @Column(name = "username", nullable = false, unique = true)
    var username: String = ""

    @Column(name = "password", nullable = false)
    var password: String = ""

    @Column(name = "first_name", nullable = false)
    var firstName: String = ""

    @Column(name = "last_name", nullable = false)
    var lastName: String = ""

    @Column(name = "biography")
    var biography: String = ""

    @Column(name = "gender")
    var gender: Gender = Gender.OTHER

    @Serializable(TimestampSerializer::class)
    @Column(name = "registration_date", nullable = false)
    var registrationDate: Timestamp = Timestamp.valueOf(LocalDateTime.now())

    @Column(name = "birthdate", nullable = false)
    var birthdate: Date = Date.valueOf(LocalDate.now())

    @OneToMany(mappedBy="user")
    var journeys: List<Journey> = listOf()

    @OneToMany(mappedBy="user")
    var reviews: List<Review> = listOf()

    @OneToMany(mappedBy="user")
    var comments: List<Comment> = listOf()

    @OneToMany(mappedBy="requester")
    var sentFollowRequests: List<FollowRequest> = listOf()

    @OneToMany(mappedBy="receiver")
    var myPendingFollowRequests: List<FollowRequest> = listOf()

    @OneToMany(mappedBy="follower")
    var following: List<Follow> = listOf()

    @OneToMany(mappedBy="followed")
    var followers: List<Follow> = listOf()

    constructor(signUpRequest: SignUpRequest) : this() {
        this.username = signUpRequest.username
        this.email = signUpRequest.email
        this.password = signUpRequest.password
        this.birthdate = signUpRequest.birthdate
        this.firstName = signUpRequest.firstName
        this.lastName = signUpRequest.lastName
    }
}


/*
create database demodb;
use demodb;
create table users (
    id int auto_increment primary key unique ,
    username varchar(100) unique ,
    email varchar(100) unique ,
    password varchar(100),
    first_name varchar(100),
    last_name varchar(100),
    registration_date datetime,
    birthdate date,
    biography varchar(500)
);
create table activities (
    id int auto_increment primary key unique ,
    journey_id int not null ,
    date date,
    title varchar(100),
    description varchar(500),
    location varchar(100)
);
create table comments (
    id int auto_increment primary key unique ,
    journey_id int not null,
    user_id int not null,
    date_posted datetime not null,
    content varchar(500) not null
);
create table destinations (
    id int auto_increment primary key unique ,
    x_cord double not null,
    y_cord double not null,
    name varchar(100)
);
create table follow (
    id int auto_increment primary key unique ,
    follow_date datetime not null,
    followed_id int not null ,
    follower_id int not null
);
create table follow_requests (
    id int auto_increment primary key unique ,
    request_date datetime not null ,
    requester_id int not null,
    receiver_id int not null
);
create table journeys (
    id int auto_increment primary key unique ,
    user_id int not null,
    start_date datetime not null,
    end_date datetime,
    destination_id int not null,
    description varchar(500)
);
create table reviews (
    id int auto_increment primary key unique ,
    user_id int not null,
    destination_id int not null,
    reviewed_date datetime not null,
    star_rating int not null,
    title varchar(100),
    content varchar(500)
);
 */