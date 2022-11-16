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
    lateinit var user: User

    @Column(name = "start_date", nullable = false)
    lateinit var startDate: Timestamp

    @Column(name = "end_date", nullable = true)
    var endDate: Timestamp? = null

    @JoinColumn(name = "destination_id", nullable = false, unique = true)
    @ManyToOne
    lateinit var destination: Destination

    @Column(name = "description", nullable = true)
    var description: String? = ""

    @OneToMany(mappedBy = "journey")
    lateinit var activities: List<Activity>

    @OneToMany(mappedBy = "journey")
    lateinit var comments: List<Comment>
}