package com.example.wishem.viewmodel

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.wishem.R
import com.example.wishem.local.OccasionEntity
import com.example.wishem.repositories.OccasionRepository
import com.example.wishem.utils.Constants.WORK_REQUEST_TAG
import com.example.wishem.utils.InjectorUtils.providesRepository
import com.example.wishem.utils.Result
import com.example.wishem.workmanager.ReminderWorker
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@Suppress("UNCHECKED_CAST")
class OccasionViewModel(
        application: Application,
        private val repo : OccasionRepository
) : AndroidViewModel(application) {


    private val _addingResult = MutableLiveData<Result<Nothing>>()
    val addingResult : LiveData<Result<Nothing>>
        get() = _addingResult


    fun addOccasion(name: String?, date: String?, month: String?, chipId: Int) = viewModelScope.launch {
        when {
            name.isNullOrEmpty() -> {
                setError("Please enter a name!")
            }
            date.isNullOrEmpty() -> {
                setError("Please enter the date!")
            }
            month.isNullOrEmpty() -> {
                setError("Please enter the month")
            }
            else -> {
                val occasionCode = when(chipId) {
                    R.id.chipBday -> 1
                    R.id.chipAnn -> 2
                    else -> 3
                }

                val occasion = OccasionEntity(name, occasionCode, date.toInt(), month.toInt())
                repo.addOccasion(occasion)
                setSuccess()
            }
        }
    }

    private fun setError(errorMsg: String) {
        _addingResult.value = Result.Error(errorMsg)
        _addingResult.value = Result.Idle

    }
    private fun setSuccess() {
        _addingResult.value = Result.Success
        _addingResult.value = Result.Idle
    }

    init {
        val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(15, TimeUnit.MINUTES)
                .addTag(WORK_REQUEST_TAG)
                .build()

        WorkManager.getInstance(application)
                .enqueueUniquePeriodicWork(WORK_REQUEST_TAG, ExistingPeriodicWorkPolicy.REPLACE, workRequest)
    }


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