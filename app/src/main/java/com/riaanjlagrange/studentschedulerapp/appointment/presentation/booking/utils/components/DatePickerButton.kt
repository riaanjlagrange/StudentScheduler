package com.riaanjlagrange.studentschedulerapp.appointment.presentation.booking.utils.components

import android.app.DatePickerDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import java.util.Calendar

@Composable
fun DatePickerButton(
    selectedDate: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    // get calendar to use with date picker
    val calendar = Calendar.getInstance()

    Button(onClick = {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                onDateSelected("$day/${month + 1}/$year")
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }) {
        Text(text = if (selectedDate.isBlank()) "Select Date" else selectedDate)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDatePickerButton() {
    DatePickerButton(
        selectedDate = "",
        onDateSelected = {}
    )
}