package com.example.wishem.repositories

import com.example.wishem.local.OccasionDao
import com.example.wishem.local.OccasionEntity

class OccasionRepository(
        private val dao: OccasionDao
) {

        suspend fun addOccasion(occasion: OccasionEntity) {
                dao.upsertOccasion(occasion)
        }

        suspend fun getAllOccasions() = dao.getAllOccasions()

        suspend fun deleteOccasion(occasion: OccasionEntity) {
                dao.deleteOccasion(occasion)
        }

        suspend fun getParticularInfo(id: Int) : OccasionEntity? = dao.getOccasion(id)

}