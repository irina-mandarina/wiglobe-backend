package com.example.demo.entities

import java.sql.Date
import java.time.LocalDate
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
    lateinit var journey: Journey

    @Column(name = "date", nullable = false)
    var date: Date = Date.valueOf(LocalDate.now())

    @Column(name = "title", nullable = false)
    var title: String = ""

    @Column(name = "description")
    var description: String = ""

    @Column(name = "location", nullable = false)
    var location: String = ""
}