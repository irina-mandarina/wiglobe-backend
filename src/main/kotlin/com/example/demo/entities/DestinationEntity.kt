package com.example.demo.entities

import javax.persistence.*
//import org.springframework.data.relational.core.mapping.Table

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

    @Column(name = "asciiname")
    var asciiName: String = ""

    @Column(name = "alternatenames")
    var alternateNames: String = ""

    @Column(name = "feature_class")
    var featureClass: String = ""

    @Column(name = "feature_code")
    var featureCode: String = ""

    @ManyToOne
    @JoinColumn(name = "country_code")
    var country: CountryEntity? = null

    @OneToMany(mappedBy = "destination")
    var reviews: List<ReviewEntity> = listOf()

    @OneToMany(mappedBy = "destination")
    var journeys: List<JourneyEntity> = listOf()
}