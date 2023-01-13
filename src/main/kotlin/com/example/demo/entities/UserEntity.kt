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
class UserEntity() {
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

    @Column(name = "last_name")
    var lastName: String? = ""

    @Column(name = "biography")
    var biography: String = ""

    @Column(name = "gender")
    var gender: Gender = Gender.OTHER

    @Serializable(TimestampSerializer::class)
    @Column(name = "registration_date")
    var registrationDate: Timestamp = Timestamp.valueOf(LocalDateTime.now())

    @Column(name = "birthdate")
    var birthdate: Date? = Date.valueOf(LocalDate.now())

    @OneToMany(mappedBy="user")
    var journeys: List<JourneyEntity> = listOf()

    @OneToMany(mappedBy="user")
    var reviews: List<ReviewEntity> = listOf()

    @OneToMany(mappedBy="user")
    var comments: List<CommentEntity> = listOf()

    @OneToMany(mappedBy="requester")
    var sentFollowRequests: List<FollowRequestEntity> = listOf()

    @OneToMany(mappedBy="receiver")
    var myPendingFollowRequests: List<FollowRequestEntity> = listOf()

    @OneToMany(mappedBy="follower")
    var following: List<FollowEntity> = listOf()

    @OneToMany(mappedBy="followed")
    var followers: List<FollowEntity> = listOf()

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

CREATE TABLE `activities` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `location` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `journey_id` bigint NOT NULL,
  `type` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8tlrdbtqfvfa951dup5e3uo0t` (`journey_id`),
  CONSTRAINT `FK8tlrdbtqfvfa951dup5e3uo0t` FOREIGN KEY (`journey_id`) REFERENCES `journeys` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `comments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `date_posted` datetime NOT NULL,
  `journey_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcl4dmg7gkwc1wwwqypkrcad9m` (`journey_id`),
  KEY `FK8omq0tc18jd43bu5tjh6jvraq` (`user_id`),
  CONSTRAINT `FK8omq0tc18jd43bu5tjh6jvraq` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKcl4dmg7gkwc1wwwqypkrcad9m` FOREIGN KEY (`journey_id`) REFERENCES `journeys` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `destinations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `x_cord` double NOT NULL,
  `y_cord` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `follow` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `follow_date` datetime NOT NULL,
  `followed_id` bigint NOT NULL,
  `follower_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9o03ye7x353hojmg9iaaybem2` (`followed_id`),
  KEY `FKjikg34txcxnhcky26w14fvfcc` (`follower_id`),
  CONSTRAINT `FK9o03ye7x353hojmg9iaaybem2` FOREIGN KEY (`followed_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKjikg34txcxnhcky26w14fvfcc` FOREIGN KEY (`follower_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `follow_requests` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `request_date` datetime NOT NULL,
  `receiver_id` bigint NOT NULL,
  `requester_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbgsle7d734bxmsqlfyd393oht` (`receiver_id`),
  KEY `FKdavofo5pfcbxtcbtvbo0tq4v9` (`requester_id`),
  CONSTRAINT `FKbgsle7d734bxmsqlfyd393oht` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKdavofo5pfcbxtcbtvbo0tq4v9` FOREIGN KEY (`requester_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `journeys` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `start_date` datetime NOT NULL,
  `destination_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `visibility` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sux05c09srjdsp9d7lgx1cnmf` (`destination_id`),
  KEY `FK1um7r8cm6rnn4dinfmarjjmqv` (`user_id`),
  CONSTRAINT `FK1um7r8cm6rnn4dinfmarjjmqv` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK5on53fhiark530qq6365v8aum` FOREIGN KEY (`destination_id`) REFERENCES `destinations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `reviews` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `reviewed_date` datetime NOT NULL,
  `star_rating` int NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `destination_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKo07xgps8spbcjhqpj559t835x` (`destination_id`),
  KEY `FKcgy7qjc1r99dp117y9en6lxye` (`user_id`),
  CONSTRAINT `FKcgy7qjc1r99dp117y9en6lxye` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKo07xgps8spbcjhqpj559t835x` FOREIGN KEY (`destination_id`) REFERENCES `destinations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `biography` varchar(255) DEFAULT NULL,
  `birthdate` date NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `registration_date` datetime NOT NULL,
  `username` varchar(255) NOT NULL,
  `gender` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


 */