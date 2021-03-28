package com.example.wishem.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface OccasionDao {

    //this will act as both insert and update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertOccasion(occasion: OccasionEntity)

    @Delete
    suspend fun deleteOccasion(occasion: OccasionEntity)

    @Query("SELECT * FROM occasions")
    suspend fun getAllOccasions() : List<OccasionEntity>

    @Query("SELECT * FROM occasions WHERE id = :id")
    suspend fun getOccasion(id: Int) : OccasionEntity?
}