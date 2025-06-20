package com.riaanjlagrange.studentschedulerapp.feedback.presentation.feedbackchatlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.riaanjlagrange.studentschedulerapp.feedback.presentation.feedbackchatlist.components.UserCard

@Composable
fun FeedbackChatListScreen(
    navController: NavController,
    viewModel: FeedbackChatListViewModel = viewModel()
) {
    val state = viewModel.state

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Start a Conversation", style = MaterialTheme.typography.titleLarge, color = Color.White, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))

        if (state.isLoading) {
            CircularProgressIndicator()
        }

        state.error?.let {
            Text("Error: $it", color = MaterialTheme.colorScheme.error)
        }

        LazyColumn {
            items(state.users) { user ->
                UserCard(user = user) {
                    navController.navigate("feedback_chat/${user.uid}")
                }
            }
        }
    }
}
