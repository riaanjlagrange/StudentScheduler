package com.riaanjlagrange.studentschedulerapp.feedback.presentation.feedback

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.UserRole
import com.riaanjlagrange.studentschedulerapp.ui.theme.PrimaryLecturer
import com.riaanjlagrange.studentschedulerapp.ui.theme.PrimaryStudent
import com.riaanjlagrange.studentschedulerapp.ui.theme.PurplePrimaryVariant

@Composable
fun FeedbackChatScreen(
    navController: NavController,
    receiverId: String,
    viewModel: FeedbackViewModel = viewModel()
) {
    val state = viewModel.state
    val roleLabelColor = when(state.yourUser?.role) {
        UserRole.Student -> PrimaryStudent
        UserRole.Lecturer -> PrimaryLecturer
        else -> PurplePrimaryVariant
    }

    // Load users and messages once
    LaunchedEffect(receiverId) {
        viewModel.loadUser()
        viewModel.loadSelectedUser(receiverId)
        viewModel.loadMessages(receiverId)
    }

    Scaffold(
        bottomBar = {
            HorizontalDivider(modifier = Modifier.padding(4.dp), thickness = 2.dp, color = PrimaryStudent)
            Row(
                Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = state.message.message,
                    onValueChange = viewModel::setMessage,
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Type a message...") }
                )
                Spacer(Modifier.width(8.dp))
                Button(onClick = {
                    viewModel.sendMessage(receiverId)
                    viewModel.setMessage("")
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Role Display Icon",
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                )
                }
            }
        }
    ) { innerPadding ->
        Column (modifier = Modifier
            .padding(innerPadding)
            .padding(10.dp)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(state.selectedUser?.name ?: "...", style = MaterialTheme.typography.titleLarge)
                    Text(
                        state.selectedUser?.email ?: "...",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
                Text(
                    state.selectedUser?.role?.name ?: "...",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(roleLabelColor)
                        .padding(5.dp)
                )
            }
            HorizontalDivider(modifier = Modifier.padding(4.dp), thickness = 2.dp, color = PrimaryStudent)
            Spacer(Modifier.height(8.dp))

            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn(
                    reverseLayout = true,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                ) {
                    items(state.messages.reversed()) { msg ->
                        //val isMe = msg.student?.uid == user?.uid || msg.lecturer?.uid == user?.uid
                        val isMe = msg.from.name == state.yourUser?.role?.name
                        val alignment = if (isMe) Alignment.End else Alignment.Start
                        Column(Modifier.fillMaxWidth(), horizontalAlignment = alignment) {
                            Surface(
                                color = if (isMe) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                                shape = MaterialTheme.shapes.medium,
                                tonalElevation = 4.dp,
                                modifier = Modifier.padding(vertical = 4.dp)
                            ) {
                                Text(
                                    msg.message,
                                    modifier = Modifier.padding(12.dp),
                                    color = if (isMe) Color.White else Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
