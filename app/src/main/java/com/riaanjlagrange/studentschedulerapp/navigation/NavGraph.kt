package com.riaanjlagrange.studentschedulerapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.riaanjlagrange.studentschedulerapp.appointment.presentation.booking.BookingScreen
import com.riaanjlagrange.studentschedulerapp.appointment.presentation.viewbookings.ViewBookingsScreen
import com.riaanjlagrange.studentschedulerapp.auth.presentation.login.LoginScreen
import com.riaanjlagrange.studentschedulerapp.auth.presentation.register.RegisterScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("booking") { BookingScreen(navController) }
        composable("view_bookings") { ViewBookingsScreen() }
    }
}