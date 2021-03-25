package com.example.wishem.utils

import android.content.Context
import com.example.wishem.local.OccasionDatabase
import com.example.wishem.repositories.OccasionRepository

object InjectorUtils {

    fun providesDatabase(context: Context) = OccasionDatabase.getOccasionDatabase(context)

    fun providesRepository(context: Context) = OccasionRepository(
            providesDatabase(context)
    )
}