package com.riaanjlagrange.studentschedulerapp.navigation

import CalendarScreen
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
import com.riaanjlagrange.studentschedulerapp.feedback.presentation.feedback.FeedbackChatScreen
import com.riaanjlagrange.studentschedulerapp.feedback.presentation.feedbackchatlist.FeedbackChatListScreen

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


        composable("view_bookings") {
            ViewBookingsScreen(navController)
        }

        composable("view_feedback") {
            FeedbackChatListScreen(navController)
        }

        composable("feedback_chat/{receiverId}") { backStackEntry ->
            val receiverId = backStackEntry.arguments?.getString("receiverId") ?: ""
            FeedbackChatScreen(navController, receiverId)
        }


        composable("calendar") {
            CalendarScreen(navController)
        }

        composable("dashboard") {
            // DashboardScreen(navController)
        }

    }
}