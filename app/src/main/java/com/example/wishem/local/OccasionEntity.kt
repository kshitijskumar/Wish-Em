package com.example.wishem.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "occasions")
data class OccasionEntity(
    var name: String? = null,
    var iconCode: Int = 3,
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
//1 - birthday
//2 - anniversary
//3 - general purpose. party
