package com.sixteenhalves.orangeadmin.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.sixteenhalves.orangeadmin.R
import com.sixteenhalves.orangeadmin.domain.NotificationPreferences

object NotificationHelper {

    private const val CHANNEL_ID = "low_stock_channel"
    private const val CHANNEL_NAME = "Low Stock Alerts"
    private const val CHANNEL_DESC = "Notifications for inventory running low"

    fun showLowStockNotification(
        context: Context,
        itemName: String,
        remainingStock: Int,
        preferences: NotificationPreferences
    ) {
        // Do not show if the user disabled low stock alerts
        if (!preferences.lowStockAlerts) return

        createNotificationChannel(context)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_inventory_2_24) // Fallback icon
            .setContentTitle("Low Stock Alert: $itemName")
            .setContentText("Only $remainingStock units remaining. Tap to restock.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        // Apply Sound & Vibration preferences
        // Note: On Android 8.0+, channels control these, but setting silent helps override dynamically
        builder.setSilent(!preferences.notificationSound)
        
        if (preferences.notificationVibration) {
            builder.setVibrate(longArrayOf(0, 500, 200, 500))
        } else {
            builder.setVibrate(longArrayOf(0))
        }

        with(NotificationManagerCompat.from(context)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission not granted, cannot show notification
                    return
                }
            }
            notify(System.currentTimeMillis().toInt(), builder.build())
        }
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESC
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
