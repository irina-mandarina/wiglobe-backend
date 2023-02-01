package com.example.demo.entities

import com.example.demo.types.InterestKeyEntityType
import javax.persistence.*

@Entity
@Table(name = "interests")
class InterestEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = -1

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    lateinit var user: UserEntity

    @Column(name = "interest_key")
    lateinit var key: String

    @Column(name = "entity")
    @Enumerated(EnumType.STRING)
    lateinit var entity: InterestKeyEntityType

    @Column(name = "value")
    var value: Double = 0.0

    @Column(name = "count")
    var count: Int = 0

    @Column(name = "last_comment_id")
    var lastCommentId: Long = -1

    constructor(user: UserEntity, key: String, keyType: InterestKeyEntityType, value: Double): this() {
        this.value = value
        this.key = key
        this.entity = keyType
        this.user = user
        this.count = 0
    }
}