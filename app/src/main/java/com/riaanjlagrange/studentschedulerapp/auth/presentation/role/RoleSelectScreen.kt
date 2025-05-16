package com.riaanjlagrange.studentschedulerapp.auth.presentation.role

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.UserRole
import androidx.compose.material3.Text

@Composable
fun RoleSelectScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Continue As", fontSize = 24.sp, modifier = Modifier.padding(bottom = 24.dp))

        Button(onClick = {
            navController.navigate("login/${UserRole.Student.name}")
        }, modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
            Text("Student")
        }

        Button(onClick = {
            navController.navigate("login/${UserRole.Lecturer.name}")
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Lecturer")
        }
    }
}

// TODO: add preview