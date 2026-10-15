package com.sixteenhalves.orangeadmin.domain

data class NotificationPreferences(
    val lowStockAlerts: Boolean = true,
    val notificationSound: Boolean = true,
    val notificationVibration: Boolean = false,
)
