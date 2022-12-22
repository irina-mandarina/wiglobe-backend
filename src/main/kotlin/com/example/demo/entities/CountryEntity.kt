package com.example.demo.entities

import javax.persistence.*

@Entity
@Table(name = "countries")
class CountryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

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
}