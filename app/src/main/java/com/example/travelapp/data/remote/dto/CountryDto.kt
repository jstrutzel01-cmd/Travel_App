package com.example.travelapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CountryDto (
    val name: Name,
    val flag: Flags?,
    val maps: Maps?,
    val currencies: Currency?,
    val capital: String,
    val language: Map<String, String>?,
    val continent: String,
    val region: String,
    val subregion: String?,
    val population: Long,
    @SerializedName("cca2")
    val countryCode:String,
)

data class Name(
    val common: String,
    val official: String
)

data class Flags(
    val png: String,
    val svg: String,
    val alt: String
)

data class Maps(
    val googleMaps: String,
    val openStreetMaps: String
)

data class Currency(
    val name: String,
    val symbol: String
)