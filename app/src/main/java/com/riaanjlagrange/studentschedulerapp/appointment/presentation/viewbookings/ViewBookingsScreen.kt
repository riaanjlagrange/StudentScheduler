package com.riaanjlagrange.studentschedulerapp.appointment.presentation.viewbookings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.riaanjlagrange.studentschedulerapp.R
import com.riaanjlagrange.studentschedulerapp.appointment.presentation.viewbookings.components.BookingItem
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.UserRole
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ViewBookingsScreen(
    navController: NavController,
    viewModel: ViewBookingsViewModel = viewModel()
) {
    val state = viewModel.state
    val role = state.yourUser?.role ?: UserRole.Student

    LaunchedEffect(Unit) {
        viewModel.loadUser()
        viewModel.loadAppointments()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("booking/${role}")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "New Booking"
                )
            }
        }
    ) {

        val refreshState = rememberPullToRefreshState()
        var isRefreshing by remember {
            mutableStateOf(false)
        }
        val coroutineScope = rememberCoroutineScope()

        PullToRefreshBox(
            state = refreshState,
            isRefreshing = isRefreshing,
            onRefresh = {
                coroutineScope.launch {
                    isRefreshing = true
                    viewModel.loadAppointments()
                    isRefreshing = false
                    refreshState.animateToHidden()
                }
            })
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
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
                            BookingItem(appointment, role)
                        }
                    }
                }
            }
        }
    }
}

//TODO: add preview