package com.riaanjlagrange.studentschedulerapp.appointment.presentation.booking

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.riaanjlagrange.studentschedulerapp.R
import com.riaanjlagrange.studentschedulerapp.appointment.presentation.booking.components.DatePickerButton
import com.riaanjlagrange.studentschedulerapp.appointment.presentation.booking.components.TimeSlotDropdown
import com.riaanjlagrange.studentschedulerapp.appointment.presentation.booking.components.UserSelectorDropdown
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.UserRole
import com.riaanjlagrange.studentschedulerapp.notification.BookingNotificationService
import com.riaanjlagrange.studentschedulerapp.utils.components.ErrorText
import com.riaanjlagrange.studentschedulerapp.utils.components.Header
import kotlinx.coroutines.delay

@Composable
fun BookingScreen(
    navController: NavController,
    selectedRole: UserRole,
    viewModel: BookingViewModel = viewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state

    val service = BookingNotificationService(context)

    val role = selectedRole

    LaunchedEffect(Unit) {
        viewModel.loadUser()
        delay(2000)
        viewModel.loadUserOptionsForBooking(role)
        viewModel.updateYourUserToState(role)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp)
    ) {
        // Header
        Header("Book an appointment with ${if (role == UserRole.Student) "Lecturer" else "Student"}:")
        Spacer(modifier = Modifier.height(16.dp))

        UserSelectorDropdown(
            selectedRole = role,
            userOptions = state.userOptions,
            selectedUser = state.selectedUser,
            onUserSelected = { viewModel.updateSelectedUser(it) }
        )


        Spacer(modifier = Modifier.height(8.dp))

        // Time slot dropdown
        TimeSlotDropdown(
            selectedTime = state.appointment.time,
            timeSlots = listOf("8:00AM", "9:00AM", "10:00AM", "11:00AM", "12:00PM", "1:00PM", "2:00PM", "3:00PM", "4:00PM"),
            onTimeSelected = { viewModel.updateTime(it) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Date picker
        DatePickerButton(state.appointment.date) {
            viewModel.updateDate(it)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Submit button
        Button(
            onClick = {
                viewModel.book { success ->
                    val msg = if (success) {
                        context.getString(R.string.booking_success)
                    } else {
                        context.getString(R.string.booking_failure)
                    }
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    if (success) {
                        service.showBookingNotification(viewModel.state.appointment)
                        navController.navigate("view_bookings") {
                            popUpTo(0)
                        }
                    }
                }
            }
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 2.dp,
                    modifier = Modifier.height(20.dp)
                )
            } else {
                Text(text = stringResource(R.string.book_now))
            }
        }

        // Error message
        state.error?.let { error ->
            Spacer(modifier = Modifier.height(8.dp))
            ErrorText(error)
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewBookingScreen() {
   BookingScreen(navController = rememberNavController(), UserRole.Student)
}
