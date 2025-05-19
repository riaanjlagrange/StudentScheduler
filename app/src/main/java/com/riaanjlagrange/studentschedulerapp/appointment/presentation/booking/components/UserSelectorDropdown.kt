package com.riaanjlagrange.studentschedulerapp.appointment.presentation.booking.components

import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.AuthUser
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.UserRole

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSelectorDropdown(
    selectedRole: UserRole,
    userOptions: List<AuthUser>,
    selectedUser: AuthUser?,
    onUserSelected: (AuthUser) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val label = "Select ${if (selectedRole == UserRole.Student) "Lecturer" else "Student"}"

    val selectedText = selectedUser?.let { "${it.name} (${it.email})" } ?: ""

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            placeholder = { Text("Tap to choose...") },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.exposedDropdownSize(matchTextFieldWidth = true)
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
