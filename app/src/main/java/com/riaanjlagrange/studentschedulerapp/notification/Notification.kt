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
            val bookingChannel = NotificationChannel(
                BookingNotificationService.BOOKING_CHANNEL_ID,
                "Bookings",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "This channel is used for bookings"
            }

            val feedbackChannel = NotificationChannel(
                BookingNotificationService.FEEDBACK_CHANNEL_ID,
                "Feedback",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "This channel is used for feedback messages"
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(bookingChannel)
            notificationManager.createNotificationChannel(feedbackChannel)
        }
    }
}