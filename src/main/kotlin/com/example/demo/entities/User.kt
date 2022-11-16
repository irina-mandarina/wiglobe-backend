package com.example.demo.entities

import com.example.demo.requestEntities.UserRequest
import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class User() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Column(name = "email", nullable = false, unique = true)
    var email: String = ""

    @Column(name = "username", nullable = false, unique = true)
    var username: String = ""

    @Column(name = "password", nullable = false, unique = true)
    var password: String = ""

    @Column(name = "first_name", nullable = false)
    var firstName: String = ""

    @Column(name = "lastName", nullable = true)
    var lastName: String = ""

    @Column(name = "registration_date", nullable = false)
    var registrationDate: Timestamp = Timestamp.valueOf(LocalDateTime.now())

    @Column(name = "biography")
    var biography: String? = ""

    @Column(name = "birthdate", nullable = false)
    var birthdate: Date = Date.valueOf(LocalDate.now())

    @OneToMany(mappedBy="user")
    var journeys: List<Journey> = listOf()

    @OneToMany(mappedBy="user")
    var reviews: List<Review> = listOf()

    @OneToMany(mappedBy="user")
    var comments: List<Comment> = listOf()

    @OneToMany(mappedBy="requester")
    var followers: List<FollowRequest> = listOf()

    @OneToMany(mappedBy="receiver")
    var followedUsers: List<FollowRequest> = listOf()

    @OneToMany(mappedBy="follower")
    var following: List<Follow> = listOf()

    constructor(userRequest: UserRequest) : this() {
        this.username = userRequest.username!!
        this.email = userRequest.email!!
        this.password = userRequest.password!!
        this.birthdate = userRequest.birthdate
        this.firstName = userRequest.firstName
        this.lastName = userRequest.lastName!!
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
    start_date timestamp not null,
    end_date timestamp,
    destination_id int not null,
    description varchar(500)
);
create table reviews (
    id int auto_increment primary key unique ,
    user_id int not null,
    destination_id int not null,
    reviewed_date timestamp not null,
    star_rating int not null,
    title varchar(100),
    content varchar(500)
);
 */