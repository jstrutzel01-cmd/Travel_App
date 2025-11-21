package com.example.travelapp.data.respository

import com.example.travelapp.data.remote.RetrofitInstance
import com.example.travelapp.data.remote.dto.CountryDto
import com.example.travelapp.domain.model.Destination

class TravelRepository {
    suspend fun getAllCountries(): List<Destination> {
        val dtoList = RetrofitInstance.api.getAllCountries()
        return dtoList.map { it.toDomain() }
    }

    private fun CountryDto.toDomain(): Destination {
        return Destination(
            id = countryCode,
            name = name.common,
            capital = capital,
            region = region,
            flagUrl = flag?.png ?: "N/A",
            population = population,
            mapUrl = maps?.googleMaps ?: "N/A",
            isSaved = false


        )
    }
}