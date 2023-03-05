package com.example.demo.entities

import javax.persistence.*

@Entity
@Table(name = "journey_images")
class JourneyImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = -1

    @JoinColumn(name = "journey_id", nullable = false)
    @ManyToOne
    lateinit var journey: JourneyEntity

    @Column(name = "filepath", nullable = false)
    lateinit var filepath: String

    constructor(journey: JourneyEntity, filepath: String) {
        this.journey = journey
        this.filepath = filepath
    }
}