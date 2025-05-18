package com.riaanjlagrange.studentschedulerapp.navigation

sealed class BottomNav(val route: String, val title: String) {
    object Dashboard : BottomNav("dashboard", "Home")
    object Bookings : BottomNav("view_bookings/Student", "Bookings")
    object Feedback : BottomNav("feedback", "Feedback")
    object Calender : BottomNav("calendar", "Calendar")
}