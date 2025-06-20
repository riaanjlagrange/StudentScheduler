package com.riaanjlagrange.studentschedulerapp.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.BigTextStyle
import com.riaanjlagrange.studentschedulerapp.MainActivity
import com.riaanjlagrange.studentschedulerapp.R
import com.riaanjlagrange.studentschedulerapp.appointment.domain.model.Appointment

class BookingNotificationService(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    fun showBookingNotification(booking: Appointment) {
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, BOOKING_CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_library_books_24)
            .setContentTitle("Appointment Booked!")
            .setContentText("Booking set for ${booking.date} at ${booking.time}.\nStudent: ${booking.student?.name} with Lecturer: ${booking.lecturer?.name}")
            .setContentIntent(activityPendingIntent)
            .build()

        notificationManager.notify(
            1,
            notification
        )
    }
    companion object {
        const val BOOKING_CHANNEL_ID = "booking_channel"
    }
}