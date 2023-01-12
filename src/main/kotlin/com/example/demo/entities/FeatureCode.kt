package com.example.demo.entities

import javax.persistence.*

@Entity
@Table(name="feature_codes")
class FeatureCode {
    @Id
    @Column(name = "code")
    var code: String = ""

    @Column(name = "meaning")
    var meaning: String = ""

    @Column(name = "description")
    var description: String = ""

    @Column(name="feature_class")
    var featureClass: String = ""

    @Column(name="feature_class_meaning")
    var featureClassMeaning: String = ""
}