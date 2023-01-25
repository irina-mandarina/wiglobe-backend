package com.example.demo.entities

import javax.persistence.*

@Entity
@Table(name = "interests")
class InterestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @JoinColumn(name = "user_id", nullable = false)
    @OneToOne
    lateinit var user: UserEntity

    @Column(name = "key")
    lateinit var key: String

    @Column(name = "value")
    var value: Double = 0.0

    @Column(name = "count")
    val count: Int = 0
}