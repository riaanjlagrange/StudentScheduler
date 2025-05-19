package com.riaanjlagrange.studentschedulerapp.appointment.presentation.booking.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSlotDropdown(
    selectedTime: String,
    timeSlots: List<String>,
    onTimeSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedTime.ifBlank { "" },
            onValueChange = {},
            label = { Text("Select Time") },
            placeholder = { Text("Choose a time slot") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.exposedDropdownSize(true)
        ) {
            timeSlots.forEach { slot ->
                DropdownMenuItem(
                    text = { Text(slot) },
                    onClick = {
                        onTimeSelected(slot)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSlotDropdown() {
    TimeSlotDropdown(
        selectedTime = "",
        timeSlots = listOf("09:00 AM", "10:00 AM", "11:00 AM"),
        onTimeSelected = {}
    )
}
