package com.example.travelapp.domain.model

data class Destination (
    val countryCode: String,
    val name: String,
    val capital: String,
    val region: String,
    val flagUrl: String,
    val population: Long,
    val mapUrl: String,
    val isSaved: Boolean=false
)

