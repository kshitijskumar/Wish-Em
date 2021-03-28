package com.example.wishem.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wishem.utils.Constants.DATABASE_NAME

@Database(entities = [OccasionEntity::class], version = 1, exportSchema = false)
abstract class OccasionDatabase : RoomDatabase() {

    abstract fun occasionDao() : OccasionDao

    companion object {

        @Volatile
        private var INSTANCE: OccasionDatabase? = null

        fun getOccasionDatabase(context: Context) : OccasionDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        OccasionDatabase::class.java,
                        DATABASE_NAME
                )
                        .fallbackToDestructiveMigration()
                        .build()

                INSTANCE = instance
                instance
            }
        }
    }

}