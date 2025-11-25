package com.example.travelapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "destinations")
data class DestinationEntity (
    @PrimaryKey
    val id: String,
    val name: String,
    val capital: String,
    val region: String,
    val flagUrl: String,
    val population: Long,
    val mapUrl: String,
    val savedTimestamp: Long,
    )
