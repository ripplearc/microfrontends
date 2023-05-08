package com.ripplearc.heavy.toolbelt.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.ripplearc.heavy.toolbelt.R
import dagger.Reusable
import javax.inject.Inject

@Reusable
class ServiceNotificationFactory @Inject constructor() {
    fun buildNotification(
        serviceContext: Context,
        pendingIntent: PendingIntent,
        elements: NotificationElements
    ): Notification {
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(
                    serviceContext,
                    elements.notificationChannelId,
                    elements.notificationChannelName,
                    elements.notificationColor
                )
            } else {
                elements.notificationChannelName
            }

        return NotificationCompat.Builder(serviceContext, channelId)
            .setContentIntent(pendingIntent)
            .setContentTitle(elements.notificationContentTitle)
            .setContentText(elements.notificationContentText)
            .setSmallIcon(elements.notificationIcon)
            .setColorized(true)
            .setColor(elements.notificationColor)
            .setStyle(NotificationCompat.BigTextStyle())
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(
        serviceContext: Context,
        channelId: String,
        channelName: String,
        channelColor: Int
    ): String {
        val channel = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        channel.lightColor = channelColor
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = serviceContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(channel)
        return channelId
    }
}