package com.ripplearc.heavy.toolbelt.notification

data class NotificationElements(
    val serviceId: Int,
    val notificationChannelId: String,
    val notificationChannelName: String,
    val notificationContentTitle: String,
    val notificationContentText: String,
    val notificationColor: Int,
    val notificationIcon: Int
)