package com.riaanjlagrange.studentschedulerapp.appointment.presentation.booking.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.AuthUser
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.UserRole

@Composable
fun UserSelectorDropdown(
    selectedRole: UserRole,
    userOptions: List<AuthUser>,
    selectedUser: AuthUser?,
    onUserSelected: (AuthUser) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text("Select ${if (selectedRole == UserRole.Student) "Lecturer" else "Student"}:")

        Text(
            text = selectedUser?.let { "${it.name} (${it.email})" } ?: "Tap to choose...",
            modifier = Modifier
                .clickable { expanded = true }
                .padding(vertical = 8.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            userOptions.forEach { user ->
                DropdownMenuItem(
                    text = { Text("${user.name} (${user.email})") },
                    onClick = {
                        onUserSelected(user)
                        expanded = false
                    }
                )
            }
        }
    }
}
