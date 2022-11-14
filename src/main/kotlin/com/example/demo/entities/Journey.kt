package com.example.demo.entities

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "journeys")
class Journey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    var user: User = User()

    @Column(name = "start_date", nullable = false)
    var startDate: Timestamp = TODO()

    @Column(name = "end_date", nullable = false)
    var endDate: Timestamp = TODO()

    @Column(name = "destination", nullable = false)
    var destination: String = ""
}