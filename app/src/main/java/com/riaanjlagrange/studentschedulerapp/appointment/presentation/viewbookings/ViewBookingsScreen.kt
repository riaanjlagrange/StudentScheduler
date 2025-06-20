package com.riaanjlagrange.studentschedulerapp.appointment.presentation.viewbookings

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
        var isRefreshing by remember { mutableStateOf(false) }
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
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.schedulerbg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    when {
                        state.isLoading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                        }

                        state.error != null -> {
                            Text(
                                text = state.error,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }

                        state.appointments.isEmpty() -> {
                            Text(
                                text = stringResource(R.string.no_appointments_found),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }

                        else -> {
                            LazyColumn {
                                items(state.appointments) { appointment ->
                                    BookingItem(appointment, role)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}