package com.example.travelapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.travelapp.data.local.dao.DestinationDao
import com.example.travelapp.data.local.entities.DestinationEntity

@Database(
    entities = [DestinationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TravelDatabase : RoomDatabase() {

    // Abstract function to get the DAO
    abstract fun destinationDao(): DestinationDao

    companion object {
        // @Volatile ensures all threads see the latest value
        @Volatile
        private var INSTANCE: TravelDatabase? = null

        // Singleton pattern - only one database instance
        fun getInstance(context: Context): TravelDatabase {
            // If instance exists, return it
            // If not, create it (synchronized prevents multiple threads creating multiple instances)
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TravelDatabase::class.java,
                    "travel_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}