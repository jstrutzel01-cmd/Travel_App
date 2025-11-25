package com.example.travelapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.travelapp.data.local.entities.DestinationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DestinationDao {
    @Query(value = "SELECT * FROM destinations ORDER BY savedTimestamp DESC")
    fun getAllDestinations() : Flow<List<DestinationEntity>>

    @Query(value = "SELECT * FROM destinations WHERE id = :destinationId ")
    suspend fun getDestinationById(destinationId: String) : DestinationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDestination(destination: DestinationEntity)

    @Delete
    suspend fun deleteDestination(destination: DestinationEntity)

    @Query("DELETE FROM destinations WHERE id = :destinationId")
    suspend fun deleteDestinationById(destinationId : String)


    @Query(value = "SELECT EXISTS(SELECT 1 FROM destinations WHERE id = :destinationId)")
    suspend fun isDestinationSaved(destinationId : String) : Boolean

}