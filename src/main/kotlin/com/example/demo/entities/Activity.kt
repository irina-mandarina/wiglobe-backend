package com.example.demo.entities

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "activities")
class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @JoinColumn(name = "journey_id", nullable = false)
    @ManyToOne
    var journey: Journey = Journey()

    @Column(name = "date", nullable = false)
    var date: Timestamp = TODO()

    @Column(name = "title", nullable = false)
    var title: String = ""

    @Column(name = "description")
    var description: String = ""

    @Column(name = "location", nullable = false)
    var location: String = ""
}