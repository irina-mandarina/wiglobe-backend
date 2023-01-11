package com.example.demo.entities

import javax.persistence.*

@Entity
@Table(name = "countries")
class CountryEntity {
    @Id
    @Column(name = "country_code", nullable = false)
    var countryCode: String = ""

    @Column(name = "country_name", nullable = false)
    var countryName: String = ""

    @Column(name = "capital")
    var capital: String = ""

    @Column(name = "area")
    var area: String = ""

    @Column(name = "continent")
    var continent: String = ""

//    @OneToMany(mappedBy = "country")
//    lateinit var destinationEntity: List<DestinationEntity>
}