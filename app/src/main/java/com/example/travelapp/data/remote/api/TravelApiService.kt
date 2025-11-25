package com.example.travelapp.data.remote.api

import android.graphics.Region
import com.example.travelapp.data.remote.dto.CountryDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TravelApiService {
    @GET("all")
    suspend fun getAllCountries(
        @Query("fields") fields: String ="name,flags,maps,currencies,capital,languages,region,subregion,population,cca2"
    ): List<CountryDto>

    @GET("name/{name}")
    suspend fun getCountryByName(@Path("name") name: String
    ): List<CountryDto>

    @GET("region/{region}")
    suspend fun getCountryByRegion(@Path("region") region: String
    ): List<CountryDto>


}