package com.riaanjlagrange.studentschedulerapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
fun AppNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = "role_select", modifier = modifier) {
        composable("role_select") {
            RoleSelectScreen(navController)
        }

        composable("login/{role}") { backStackEntry ->
            val roleString = backStackEntry.arguments?.getString("role") ?: "student"
            val selectedRole = UserRole.valueOf(roleString)
            LoginScreen(navController, selectedRole)
        }

        composable("register") {
            RegisterScreen(navController)
        }

        composable("booking/{role}") { backStackEntry ->
            val roleString = backStackEntry.arguments?.getString("role") ?: "student"
            val selectedRole = UserRole.valueOf(roleString)
            BookingScreen(navController, selectedRole)
        }

        composable("booking") {
            // fallback
            BookingScreen(navController, UserRole.Student)
        }


        composable("view_bookings/{role}") { backStackEntry ->
            val roleString = backStackEntry.arguments?.getString("role") ?: "Student"
            val selectedRole = UserRole.valueOf(roleString)
            ViewBookingsScreen(navController, selectedRole)
        }


        composable("view_bookings") {
            // fallback version if no role passed
            ViewBookingsScreen(navController, UserRole.Student)
        }

        composable("feedback") {
            // FeedbackScreen(navController)
        }

        composable("calendar") {
            // CalendarScreen(navController)
        }

        composable("student_dashboard") {
            // CalendarScreen(navController)
        }

        composable("lecturer_dashboard") {
            // CalendarScreen(navController)
        }
    }
}