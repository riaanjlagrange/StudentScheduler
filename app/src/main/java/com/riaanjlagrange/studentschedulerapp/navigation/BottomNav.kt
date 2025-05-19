package com.riaanjlagrange.studentschedulerapp.navigation

sealed class BottomNav(val route: String, val title: String) {
    object Dashboard : BottomNav("dashboard", "Home")
    object Bookings : BottomNav("view_bookings", "Bookings")
    object Feedback : BottomNav("view_feedback", "Feedback")
    object Calender : BottomNav("calendar", "Calendar")
}