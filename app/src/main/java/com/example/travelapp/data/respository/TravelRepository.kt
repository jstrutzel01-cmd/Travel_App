package com.example.travelapp.data.respository

import com.example.travelapp.data.local.dao.DestinationDao
import com.example.travelapp.data.remote.RetrofitInstance
import com.example.travelapp.domain.model.Destination
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import toDomain
import toDomainList
import toEntity

class TravelRepository(val dao: DestinationDao) {

    // API OPERATIONS
    suspend fun getAllCountries(): List<Destination> {
        val dtoList = RetrofitInstance.api.getAllCountries()
        return dtoList.map { it.toDomain() }
    }

    suspend fun getCountryByName(query: String): List<Destination> {
        val dtoList = RetrofitInstance.api.getCountryByName(name = query)
        return dtoList.map { it.toDomain() }
    }

    suspend fun getCountryByRegion(region: String): List<Destination> {
        val dtoList = RetrofitInstance.api.getCountryByRegion(region = region)  // ✅ FIXED
        return dtoList.map { it.toDomain() }
    }

    // DB Operation
    fun getSavedDestinations() : Flow<List<Destination>> {
        return dao.getAllDestinations()
            .map { entities ->
                entities.toDomainList()
            }
    }

    suspend fun saveDestination(destination: Destination) {
        val entity = destination.toEntity()
        dao.insertDestination(entity)
    }

    suspend fun unsaveDestination(destination: Destination) {
        dao.deleteDestinationById(destination.countryCode)
    }

    suspend fun isDestinationSaved(countryCode: String) : Boolean {
        return dao.isDestinationSaved(countryCode)
    }

    suspend fun getCountriesWithSavedStatus(destinations : List<Destination> ) : List<Destination> {
        return destinations.map { destination ->
            val isSaved = dao.isDestinationSaved(destination.countryCode)
            destination.copy(isSaved = isSaved)
        }
    }

}