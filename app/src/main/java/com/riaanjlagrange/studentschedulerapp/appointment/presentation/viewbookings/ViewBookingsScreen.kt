package com.riaanjlagrange.studentschedulerapp.appointment.presentation.viewbookings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.riaanjlagrange.studentschedulerapp.R
import com.riaanjlagrange.studentschedulerapp.appointment.presentation.viewbookings.components.BookingItem
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.UserRole
import com.riaanjlagrange.studentschedulerapp.utils.components.Header

@Composable
fun ViewBookingsScreen(
    navController: NavController,
    selectedRole: UserRole,
    viewModel: ViewBookingsViewModel = viewModel()
) {
    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.loadAppointments()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Header(title = stringResource(R.string.bookings_header))

        // Loading
        if (state.isLoading) {
            CircularProgressIndicator()
        }
        // Error
        else if (state.error != null) {
            Text(text = state.error)
        }
        // No appointments
        else if (state.appointments.isEmpty()) {
            Text(text = stringResource(R.string.no_appointments_found))
        }
        // Show list
        else {
            LazyColumn {
                items(state.appointments) { appointment ->
                    // checks if user is a lecturer or not. and displays differently depending on it
                    BookingItem(appointment, selectedRole)
                }
            }
        }
    }
}

//TODO: add preview