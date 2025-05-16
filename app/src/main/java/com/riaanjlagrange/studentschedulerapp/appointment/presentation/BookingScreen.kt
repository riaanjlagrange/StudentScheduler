package com.riaanjlagrange.studentschedulerapp.appointment.presentation

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
import com.riaanjlagrange.studentschedulerapp.appointment.presentation.components.DatePickerButton
import com.riaanjlagrange.studentschedulerapp.appointment.presentation.components.TimeSlotDropdown
import com.riaanjlagrange.studentschedulerapp.components.Header

@Composable
fun BookingScreen(
    navController: NavController,
    viewModel: BookingViewModel = viewModel()
) {
    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp)
    ) {
        // set bookings header
        Header("Book appointment")

        Spacer(modifier = Modifier.height(16.dp))

        // button to pick date from timeslot dropdown
        DatePickerButton(viewModel.selectedDate) {
            viewModel.selectedDate = it
        }

        Spacer(modifier = Modifier.height(8.dp))

        // timeslot dropdown to select date from dropdown list
        TimeSlotDropdown(
            selectedTime = viewModel.selectedTime,
            timeSlots = listOf("09:00 AM", "10:00 AM", "11:00 AM", "01:00 PM", "03:00 PM"),
            onTimeSelected = { viewModel.selectedTime = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // submit booking and give feedback
        Button(onClick = {
            viewModel.book { success ->
                val msg = if (success) {
                    context.getString(R.string.booking_success)
                } else {
                    context.getString(R.string.booking_failure)
                }
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                if (success) {
                    // navigate after booking is confirmed
                    navController.navigate("confirmation") // Or back to home
                }
            }
        }) {
            if (viewModel.isBooking) CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp)
            else Text(stringResource(R.string.book_now))
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
