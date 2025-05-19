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
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.riaanjlagrange.studentschedulerapp.appointment.domain.model.Appointment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.UserRole
import com.riaanjlagrange.studentschedulerapp.ui.theme.PrimaryLecturer
import com.riaanjlagrange.studentschedulerapp.ui.theme.PrimaryLecturerBackground
import com.riaanjlagrange.studentschedulerapp.ui.theme.PrimaryStudent
import com.riaanjlagrange.studentschedulerapp.ui.theme.PrimaryStudentBackground

@Composable
fun BookingItem(appointment: Appointment, selectedRole: UserRole) {

    val isBookedByMe = appointment.userId == FirebaseAuth.getInstance().currentUser?.uid

    val roleColor = when(selectedRole) {
        UserRole.Student -> PrimaryStudentBackground
        UserRole.Lecturer -> PrimaryLecturerBackground
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = roleColor
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // displays the appointment, booker, date and time
            Text("Date: ${appointment.date}")
            Text("Time: ${appointment.time}")

            Spacer(modifier = Modifier.height(4.dp))

            // this is a mess don't blame me TODO: clean up
            if (isBookedByMe) {
                if (selectedRole == UserRole.Student) {
                    Text("Appointment with Lecturer: ${appointment.lecturer?.name}")
                } else {
                    Text("Appointment with Student: ${appointment.student?.name}")
                }
                Text("Booked by you")
            } else if(selectedRole == UserRole.Student) {
                Text("Appointment with Lecturer: ${appointment.lecturer?.name}")
                Text("Booked by ${appointment.lecturer?.name}")
            } else if(selectedRole == UserRole.Lecturer) {
                Text("Appointment with Student: ${appointment.student?.name}")
                Text("Booked by ${appointment.student?.name}")
            } else {
                Text("Not sure who booked this..")
            }
        }
    }
}
