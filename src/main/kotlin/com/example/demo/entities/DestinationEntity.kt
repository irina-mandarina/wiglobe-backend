package com.example.demo.entities

import javax.persistence.*

@Entity
@Table(name = "geonames")
class DestinationEntity {
    @Id
    @Column(name = "geonameid", nullable = false)
    var id: Long? = null

    @Column(name = "latitude", nullable = false)
    var latitude: Double = 0.0

    @Column(name = "longitude", nullable = false)
    var longitude: Double = 0.0

    @Column(name = "name")
    var name: String = ""

    @Column(name = "feature_class")
    var featureClass: String = ""

    @Column(name = "feature_code")
    var featureCode: String = ""

    @ManyToOne
    @JoinColumn(name = "country_code")
    lateinit var country: CountryEntity

    @OneToMany(mappedBy = "destination")
    var reviews: List<ReviewEntity> = listOf()

    @OneToMany(mappedBy = "destination")
    var journeys: List<JourneyEntity> = listOf()
}