package com.riaanjlagrange.studentschedulerapp.feedback.presentation.feedbackchatlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.*
import com.google.firebase.auth.FirebaseAuth
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.UserRole
import com.riaanjlagrange.studentschedulerapp.core.data.repository.UsersRepositoryImp
import kotlinx.coroutines.launch

class FeedbackChatListViewModel : ViewModel() {
    private val repo = UsersRepositoryImp()

    var state by mutableStateOf(FeedbackChatListViewState())
        private set

    init {
        loadOppositeRoleUsers()
    }

    private fun loadOppositeRoleUsers() {
        state = state.copy(isLoading = true)

        viewModelScope.launch {
            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            val currentUserResult = repo.getUserById(currentUserId)

            currentUserResult.fold(
                ifLeft = { error ->
                    state = state.copy(error = error.error.message, isLoading = false)
                },
                ifRight = { user ->
                    val oppositeRole = when (user.role) {
                        UserRole.Student -> UserRole.Lecturer
                        UserRole.Lecturer -> UserRole.Student
                    }

                    val userResult = when (oppositeRole) {
                        UserRole.Student -> repo.getAllStudents()
                        UserRole.Lecturer -> repo.getAllLecturers()
                    }

                    userResult.fold(
                        ifLeft = { error ->
                            state = state.copy(error = error.error.message, isLoading = false)
                        },
                        ifRight = { users ->
                            state = state.copy(users = users, isLoading = false)
                        }
                    )
                }
            )
        }
    }
}
