package com.riaanjlagrange.studentschedulerapp.appointment.presentation.viewbookings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.riaanjlagrange.studentschedulerapp.R
import com.riaanjlagrange.studentschedulerapp.appointment.domain.model.Appointment
import com.riaanjlagrange.studentschedulerapp.appointment.domain.model.getCounterPartyName
import com.riaanjlagrange.studentschedulerapp.appointment.domain.model.getCounterPartyRole
import androidx.compose.ui.graphics.Color

@Composable
fun BookingItem(appointment: Appointment) {

    val isBookedByMe = appointment.userId == FirebaseAuth.getInstance().currentUser?.uid

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // displays the appointment, booker, date and time
            Text("Appointment with ${appointment.getCounterPartyRole()}: ${appointment.getCounterPartyName()}")
            Text("Date: ${appointment.date}")
            Text("Time: ${appointment.time}")

            Spacer(modifier = Modifier.height(4.dp))

            if (isBookedByMe) {
                Text("Booked by you", color = Color.Gray)
            } else if(appointment.lecturer != null) {
                Text("Booked by ${appointment.lecturer.name}", color = Color.Gray)
            } else if(appointment.student != null) {
                Text("Booked by ${appointment.student.name}", color = Color.Gray)
            } else {
                Text("Not sure who booked this..", color = Color.Gray)
            }
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