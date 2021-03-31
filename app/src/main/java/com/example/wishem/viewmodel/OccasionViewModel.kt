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
import com.example.wishem.utils.UtilsFunctions.getTodaysEvents
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

    private val _todaysOccasion = MutableLiveData<Result<List<OccasionEntity>>>()
    val todaysOccasion : LiveData<Result<List<OccasionEntity>>>
        get() = _todaysOccasion

    private val _allOccasion = MutableLiveData<Result<List<OccasionEntity>>>()
    val allOccasion : LiveData<Result<List<OccasionEntity>>>
        get() = _allOccasion

    private val _particularInfo = MutableLiveData<Result<OccasionEntity>>()
    val particularInfo : LiveData<Result<OccasionEntity>>
        get() = _particularInfo


    suspend fun updateOccasion() = viewModelScope.launch {
        _todaysOccasion.value = Result.Loading
        _allOccasion.value = Result.Loading

        val allEvents = repo.getAllOccasions().sortedWith(compareBy({it.month}, {it.date}))

        val todaysEvents = getTodaysEvents(allEvents)

        _todaysOccasion.value = Result.Success(todaysEvents)
        _allOccasion.value = Result.Success(allEvents)
//
//        _todaysOccasion.value = Result.Idle
//        _allOccasion.value = Result.Idle
    }

    fun getParticularInfo(id: Int) = viewModelScope.launch {
        _particularInfo.value = Result.Loading
        val info = repo.getParticularInfo(id)
        if (info != null) {
            _particularInfo.value = Result.Success(info)
            _particularInfo.value = Result.Idle
        }else {
            _particularInfo.value = Result.Error("Sorry, info not found!")
            _particularInfo.value = Result.Idle
        }

    }

    fun addOccasion(name: String?, date: String?, month: String?, chipId: Int) = viewModelScope.launch {
        when {
            name.isNullOrEmpty() -> {
                setAddingError("Please enter a name!")
            }
            date.isNullOrEmpty() -> {
                setAddingError("Please enter the date!")
            }
            month.isNullOrEmpty() -> {
                setAddingError("Please enter the month")
            }
            else -> {
                val occasionCode = when(chipId) {
                    R.id.chipBday -> 1
                    R.id.chipAnn -> 2
                    else -> 3
                }

                val occasion = OccasionEntity(name, occasionCode, date.toInt(), month.toInt())
                repo.addOccasion(occasion)
                updateOccasion()
                setAddingSuccess()
            }
        }
    }

    fun deleteOccasion(occasion: OccasionEntity) = viewModelScope.launch {
        repo.deleteOccasion(occasion)
        updateOccasion()
    }

    private fun setAddingError(errorMsg: String) {
        _addingResult.value = Result.Error(errorMsg)
        _addingResult.value = Result.Idle

    }
    private fun setAddingSuccess() {
        _addingResult.value = Result.EmptySuccess
        _addingResult.value = Result.Idle
    }

    init {
        viewModelScope.launch {
            updateOccasion()
        }

        val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(12, TimeUnit.HOURS)
                .addTag(WORK_REQUEST_TAG)
                .build()

        WorkManager.getInstance(application)
                .enqueueUniquePeriodicWork(WORK_REQUEST_TAG, ExistingPeriodicWorkPolicy.KEEP, workRequest)
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