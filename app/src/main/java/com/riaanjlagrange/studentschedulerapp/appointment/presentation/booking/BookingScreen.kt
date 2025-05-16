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
import com.riaanjlagrange.studentschedulerapp.utils.components.ErrorText
import com.riaanjlagrange.studentschedulerapp.utils.components.Header

@Composable
fun BookingScreen(
    navController: NavController,
    viewModel: BookingViewModel = viewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp)
    ) {
        // Header
        Header("Book appointment")
        Spacer(modifier = Modifier.height(16.dp))

        // Date picker
        DatePickerButton(state.appointment.date) {
            viewModel.updateDate(it)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Time slot dropdown
        TimeSlotDropdown(
            selectedTime = state.appointment.time,
            timeSlots = listOf("09:00 AM", "10:00 AM", "11:00 AM", "01:00 PM", "03:00 PM"),
            onTimeSelected = { viewModel.updateTime(it) }
        )

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
                        navController.navigate("view_bookings")
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
   BookingScreen(navController = rememberNavController())
}
