package com.riaanjlagrange.studentschedulerapp.auth.presentation.role.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.riaanjlagrange.studentschedulerapp.ui.theme.PrimaryLecturer
import com.riaanjlagrange.studentschedulerapp.ui.theme.PrimaryStudent

@Composable
fun RoleCard(navController: NavController, role: String) {
    val primaryColor = when(role) {
        "Student" -> PrimaryStudent
        "Lecturer" -> PrimaryLecturer
        else -> Color.Red
    }

    val description: String = when(role ) {
        "Student" -> "Book appointments with lecturers"
        "Lecturer" -> "Manage your availability"
        else -> "Learn something"
    }

    val displayIcon: ImageVector = when(role) {
        "Student" -> Icons.Filled.Create
        "Lecturer" -> Icons.Filled.DateRange
        else -> Icons.Filled.AccountCircle
    }

    Box(
        modifier = Modifier
            .height(200.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, primaryColor, RoundedCornerShape(16.dp))
            .drawBehind {
                val strokeWidth = 5.dp.toPx()
                val y = size.height - strokeWidth / 2
                drawLine(
                    color = primaryColor,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            }
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
        ) {
            Text(
                text = role,
                fontSize = 18.sp,
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = description,
                fontSize = 12.sp,
            )
        }

        Icon(
            imageVector = displayIcon,
            contentDescription = "Role Display Icon",
            tint = primaryColor,
            modifier = Modifier
                .align(Alignment.Center)
                .size(30.dp)
        )

        Button(onClick = {
            navController.navigate("login/$role")
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Continue as $role")
        }
    }
}