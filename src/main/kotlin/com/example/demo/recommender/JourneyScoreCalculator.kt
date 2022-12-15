package com.example.demo.recommender

import com.example.demo.entities.JourneyEntity

fun calculateJourneyScore(journeyEntity: JourneyEntity): Double {
    // this score is based on the amount of journey parameters that a user is likes
    var score: Double = 0.0

    /* the comments and reviews of a user need to be observed so we know what kinds of:
    - countries; -features; -activity types;
        a user likes (posts positive comments on) */


    // consider the destination
    // a destination has parameters such as: country, feature type/class


    // consider some activity types

    return score;
}