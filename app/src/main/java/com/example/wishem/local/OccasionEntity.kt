package com.example.wishem.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Occasions")
data class OccasionEntity(
    var name: String? = null,
    var occasion: String? = null,
    var iconCode: Int = 0,
    var date: Int = 1,
    var month: Int = 1,
    var phoneNum: String? = null
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    val dateToDisplay: String
        get() = "$date / $month"
}

//icon codes
//0 - general purpose. party
//1 - birthday
//2 - anniversary