package com.sats.bagel.wear

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import androidx.wear.ongoing.OngoingActivity
import androidx.wear.ongoing.Status
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BagelService : LifecycleService() {

    private val bagelRepository: BagelRepository by lazy {
        (application as BagelApplication).repository
    }

    private lateinit var notificationManager: NotificationManager

    private val notificationChannelId = "bagel_notification_id"

    private val notificationId = 1

    private val localBinder = LocalBinder()

    private var configurationChange = false
    private var serviceRunningInForeground = false

    override fun onCreate() {
        super.onCreate()

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        bagelRepository.bagelCount.onEach {
            println("Bagel count: $it")
            val notification = generateNotification("Counter changed")
            notificationManager.notify(notificationId, notification)
        }.launchIn(lifecycleScope)
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)

        notForegroundService()
        return localBinder
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        notForegroundService()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        super.onUnbind(intent)

        if (!configurationChange) {
            val notification = generateNotification("Bagel counter is running in background")
            startForeground(notificationId, notification)
            serviceRunningInForeground = true
        }
        return true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        return Service.START_NOT_STICKY
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        configurationChange = true
    }

    private fun notForegroundService() {
        stopForeground(true)
        serviceRunningInForeground = false
        configurationChange = false
    }

    fun startBagel() {
        startService(Intent(applicationContext, BagelService::class.java))
    }



    private fun generateNotification(message: String): Notification {
        val title = "Bagel Counter"

        val notificationChannel = NotificationChannel(
            notificationChannelId,
            title,
            NotificationManager.IMPORTANCE_DEFAULT,
        )

        notificationManager.createNotificationChannel(notificationChannel)

        val bigTextStyle =
            NotificationCompat.BigTextStyle().bigText(message).setBigContentTitle(title)

        val launchActivityIntent = Intent(this, MainActivity::class.java)

        val activityPendingIntent = PendingIntent.getService(
            this, 0, launchActivityIntent, PendingIntent.FLAG_IMMUTABLE
        )

        val notificationCompatBuilder =
            NotificationCompat.Builder(applicationContext, notificationChannelId)

        val notificationBuilder = notificationCompatBuilder
            .setStyle(bigTextStyle)
            .setContentText(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_bagel)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setOngoing(true)
            .setCategory(NotificationCompat.CATEGORY_WORKOUT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        val ongoingActivityStatus = Status.Builder().addTemplate(message).build()

        val ongoingActivity = OngoingActivity
            .Builder(applicationContext, notificationId, notificationBuilder)
            .setStaticIcon(R.drawable.ic_bagel)
            .setTouchIntent(activityPendingIntent)
            .setStatus(ongoingActivityStatus)
            .build()

        ongoingActivity.apply(applicationContext)

        return notificationBuilder.build()

    }

    inner class LocalBinder: Binder() {
        internal val bagelService : BagelService
            get() = this@BagelService
    }

}