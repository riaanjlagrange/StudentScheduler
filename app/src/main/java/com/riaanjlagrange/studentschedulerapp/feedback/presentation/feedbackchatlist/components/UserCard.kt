package com.riaanjlagrange.studentschedulerapp.feedback.presentation.feedbackchatlist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.AuthUser
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.UserRole
import com.riaanjlagrange.studentschedulerapp.ui.theme.PrimaryLecturer
import com.riaanjlagrange.studentschedulerapp.ui.theme.PrimaryLecturerBackground
import com.riaanjlagrange.studentschedulerapp.ui.theme.PrimaryStudent
import com.riaanjlagrange.studentschedulerapp.ui.theme.PrimaryStudentBackground


@Composable
fun UserCard(user: AuthUser, onClick: () -> Unit) {

    val roleBackgroundColor = when(user.role) {
        UserRole.Student -> PrimaryStudentBackground
        UserRole.Lecturer -> PrimaryLecturerBackground
    }

    val roleForegroundColor = when(user.role) {
        UserRole.Student -> PrimaryStudent
        UserRole.Lecturer -> PrimaryLecturer
    }

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .fillMaxSize(),
        colors = CardDefaults.cardColors(
            containerColor = roleBackgroundColor
        )
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(text = user.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = user.email, style = MaterialTheme.typography.bodySmall)
            }
            Text(
                text = user.role.name,
                color = roleForegroundColor,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
