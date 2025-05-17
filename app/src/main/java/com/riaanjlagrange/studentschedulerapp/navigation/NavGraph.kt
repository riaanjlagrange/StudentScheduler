package com.riaanjlagrange.studentschedulerapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.riaanjlagrange.studentschedulerapp.appointment.presentation.booking.BookingScreen
import com.riaanjlagrange.studentschedulerapp.appointment.presentation.viewbookings.ViewBookingsScreen
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.UserRole
import com.riaanjlagrange.studentschedulerapp.auth.presentation.login.LoginScreen
import com.riaanjlagrange.studentschedulerapp.auth.presentation.register.RegisterScreen
import com.riaanjlagrange.studentschedulerapp.auth.presentation.role.RoleSelectScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "role_select") {
        composable("role_select") { RoleSelectScreen(navController) }
        composable("login/{role}") { backStackEntry ->
            val roleString = backStackEntry.arguments?.getString("role") ?: "student"
            val selectedRole = UserRole.valueOf(roleString)
            LoginScreen(navController, selectedRole)
        }
        composable("register") { RegisterScreen(navController) }
        composable("booking/{role}") { backStackEntry ->
            val roleString = backStackEntry.arguments?.getString("role") ?: "student"
            val selectedRole = UserRole.valueOf(roleString)
            BookingScreen(navController, selectedRole)
        }
        composable("view_bookings") { ViewBookingsScreen(navController) }
    }
}