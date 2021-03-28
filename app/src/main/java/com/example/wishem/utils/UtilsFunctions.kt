package com.example.wishem.utils

import android.view.View
import com.example.wishem.local.OccasionEntity
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

object UtilsFunctions {

    fun View.showErrorSB(text: String){
        Snackbar.make(this, text, Snackbar.LENGTH_SHORT).show()
    }

    fun View.showAddSuccessSB() {
        Snackbar.make(this, "Details added successfully!", Snackbar.LENGTH_SHORT).show()
    }

    fun getCurrentDateMonth() : String{
        val sdf = SimpleDateFormat("dd / MM", Locale.getDefault())
        return sdf.format(Date())
    }

    fun mapIdToOccasion(id: Int) : String  = when(id) {
        1 -> "Wish Birthday"
        2 -> "Wish Anniversary"
        else -> "You have something to wish"
    }

    fun getTodaysEvents(occasionList: List<OccasionEntity>) : MutableList<OccasionEntity> {
        val todaysOccasion = mutableListOf<OccasionEntity>()
        val currentDate = getCurrentDateMonth()

        occasionList.forEach {
            if("${if(it.date < 10) "0" else ""}${it.date} / ${if(it.month < 10) "0" else ""}${it.month}" == currentDate) {
                todaysOccasion.add(it)
            }
        }

        return todaysOccasion
    }
}