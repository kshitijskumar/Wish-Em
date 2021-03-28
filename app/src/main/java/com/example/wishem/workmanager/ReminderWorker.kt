package com.example.wishem.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.wishem.FeaturesActivity
import com.example.wishem.R
import com.example.wishem.local.OccasionEntity
import com.example.wishem.utils.Constants.CHANNEL_DESCRIPTION
import com.example.wishem.utils.Constants.CHANNEL_ID
import com.example.wishem.utils.Constants.CHANNEL_NAME
import com.example.wishem.utils.InjectorUtils.providesRepository
import com.example.wishem.utils.UtilsFunctions.getTodaysEvents
import com.example.wishem.utils.UtilsFunctions.mapIdToOccasion

class ReminderWorker(val context: Context, params: WorkerParameters) : CoroutineWorker(context.applicationContext, params) {

//    val repo = providesRepository(context)

    override suspend fun doWork(): Result {
        Log.d("Worker", "in do work")

        val occasionsList = providesRepository(context).getAllOccasions()

        Log.d("Worker", "after occasions list with $occasionsList")


        if (occasionsList.isNullOrEmpty()) {
            return Result.failure(workDataOf("ErrorMsg" to "No data stored"))
        }

        val todaysOccasion: MutableList<OccasionEntity> = getTodaysEvents(occasionsList)

        if (todaysOccasion.isEmpty()) {
            Log.d("Worker", "todays occasion empty")
            return Result.failure(workDataOf("ErrorMsg" to "No occasions today"))
        }
        showNotification(todaysOccasion)

        return Result.success()
    }

    private fun showNotification(today: MutableList<OccasionEntity>) {

        Log.d("Worker", "in show notification with $today")
        val notiText = when(today.size) {
            1 -> "${mapIdToOccasion(today[0].iconCode)} to ${today[0].name}"
            else -> "You have some occasions to wish to some people!"
        }

        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_cake)
                .setContentTitle("Wish'Em")
                .setContentText(notiText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = CHANNEL_DESCRIPTION
            }
            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(applicationContext, FeaturesActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)

        builder.setContentIntent(pendingIntent)
                .setAutoCancel(true)

        with(NotificationManagerCompat.from(applicationContext)){
            notify(1, builder.build())
        }
    }
}