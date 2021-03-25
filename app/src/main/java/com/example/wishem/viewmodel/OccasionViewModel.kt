package com.example.wishem.viewmodel

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wishem.repositories.OccasionRepository
import com.example.wishem.utils.InjectorUtils.providesRepository

@Suppress("UNCHECKED_CAST")
class OccasionViewModel(
        application: Application,
        private val repo : OccasionRepository
) : AndroidViewModel(application) {



    companion object {
        private class OccasionViewModelFactory(private val application: Application, private val context: Context) : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = providesRepository(context)
                return OccasionViewModel(application, repo) as T
            }
        }

        fun getOccasionViewModel(owner: FragmentActivity, application: Application, context: Context) : OccasionViewModel {
            return ViewModelProvider(owner, OccasionViewModelFactory(application, context))[OccasionViewModel::class.java]
        }
    }
}