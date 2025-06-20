package com.riaanjlagrange.studentschedulerapp.auth.presentation.role

import com.riaanjlagrange.studentschedulerapp.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.UserRole
import androidx.compose.ui.res.painterResource
import com.google.firebase.auth.FirebaseAuth
import com.riaanjlagrange.studentschedulerapp.auth.presentation.role.components.RoleCard

@Composable
fun RoleSelectScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val user = FirebaseAuth.getInstance().currentUser
        LaunchedEffect(user) {
            if (user != null) {
                navController.navigate("view_bookings") {
                    popUpTo("role_select") { inclusive = true }
                }
            }
        }
        // TODO: update with higher res image
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        RoleCard(navController, UserRole.Student.name)

        Spacer(modifier = Modifier.height(8.dp))

        RoleCard(navController, UserRole.Lecturer.name)


    }
}

// TODO: add preview