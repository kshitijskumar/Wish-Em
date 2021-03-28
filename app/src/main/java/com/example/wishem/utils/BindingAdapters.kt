package com.example.wishem.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.wishem.R

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("event_icon")
    fun eventIcon(view: ImageView, iconCode: Int) {
        when(iconCode) {
            1 -> {
                view.setImageResource(R.drawable.ic_birthday)
            }
            2 -> {
                view.setImageResource(R.drawable.ic_anniversary)
            }
            else -> {
                view.setImageResource(R.drawable.ic_party_other)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("occasion_date", "occasion_month")
    fun occasionDate(view: TextView, date: Int, month: Int) {
        view.text = "${if(date < 10) "0" else ""}${date} / ${if(month < 10) "0" else ""}${month}"
    }

    @JvmStatic
    @BindingAdapter("event_name")
    fun eventIcon(view: TextView, iconCode: Int) {
        when(iconCode) {
            1 -> {
                view.text = "Birthday"
            }
            2 -> {
                view.text = "Anniversary"
            }
            else -> {
                view.text = "Some event"
            }
        }
    }
}