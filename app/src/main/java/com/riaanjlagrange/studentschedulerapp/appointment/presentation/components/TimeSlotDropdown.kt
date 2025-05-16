package com.riaanjlagrange.studentschedulerapp.appointment.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TimeSlotDropdown(
    selectedTime: String,
    timeSlots: List<String>,
    onTimeSelected: (String) -> Unit
) {
    // set default state to not expand dropdown
    var expanded by remember { mutableStateOf(false) }

    Box {
        // button click expands dropdown
        Button(onClick = { expanded = true }) {
            Text(if (selectedTime.isBlank()) "Select Time" else selectedTime)
        }
    }

    // set params to value of expanded, and set expanded to false upon dismissal
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        // populate timeSlot items
        timeSlots.forEach { slot ->
            DropdownMenuItem(
                text = { Text(slot) },
                onClick = {
                    // set time selected to state
                    onTimeSelected(slot)
                    expanded = false
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSlotDropdown() {
    TimeSlotDropdown(
        selectedTime = "",
        timeSlots = listOf(""),
        onTimeSelected = {}
    )
}