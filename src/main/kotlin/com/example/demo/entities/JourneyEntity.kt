package com.example.demo.entities

import com.example.demo.types.Visibility
import java.util.Date
import java.sql.Timestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "journeys")
class JourneyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    lateinit var user: UserEntity

    @Column(name = "posted_on", nullable = false)
    var postedOn: Timestamp = Timestamp.valueOf(LocalDateTime.now())

    @Column(name = "start_date")
    var startDate: Timestamp? = null

    @Column(name = "end_date")
    var endDate: Timestamp? = null

    @JoinColumn(name = "destination_id")
    @ManyToOne
    var destination: DestinationEntity? = null

    @Column(name = "description", nullable = true)
    var description: String? = ""

    @Column(name = "visibility")
    @Enumerated(EnumType.STRING)
    var visibility: Visibility = Visibility.PUBLIC

    @OneToMany(mappedBy = "journey")
    var activities: List<ActivityEntity> = listOf()

    @OneToMany(mappedBy = "journey")
    lateinit var comments: List<CommentEntity>
}