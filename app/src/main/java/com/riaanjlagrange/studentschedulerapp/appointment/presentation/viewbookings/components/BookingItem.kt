package com.riaanjlagrange.studentschedulerapp.appointment.presentation.viewbookings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.riaanjlagrange.studentschedulerapp.R
import com.riaanjlagrange.studentschedulerapp.appointment.domain.model.Appointment

@Composable
fun BookingItem(appointment: Appointment) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // displays the appointment date and time
            Text("Date: ${appointment.date}")
            Text("Time: ${appointment.time}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewComposable() {
    BookingItem(
        Appointment(
            id = stringResource(R.string.default_uuid),
            userId = stringResource(R.string.default_uuid),
            date = stringResource(R.string.default_date),
            time = stringResource(R.string.default_time)
        )
    )
}