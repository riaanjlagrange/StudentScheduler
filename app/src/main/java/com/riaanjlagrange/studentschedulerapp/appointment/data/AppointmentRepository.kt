package com.riaanjlagrange.studentschedulerapp.appointment.data

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class AppointmentRepository {
    private val db = Firebase.firestore

    // set booking document in appointments collection
    // set onResult lambda to be used in BookingViewModel
    fun bookAppointment(appt: Appointment, onResult: (Boolean) -> Unit) {
        db.collection("appointments")
            .document(appt.id)
            .set(appt)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }
}

