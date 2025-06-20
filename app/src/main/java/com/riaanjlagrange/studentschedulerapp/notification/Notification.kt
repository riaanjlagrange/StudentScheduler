package com.riaanjlagrange.studentschedulerapp.notification

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class Notification: Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                BookingNotificationService.BOOKING_CHANNEL_ID,
                "Bookings", // name you see in the app settings
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "This channel is used for bookings" // description

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}