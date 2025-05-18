package com.riaanjlagrange.studentschedulerapp.feedback.presentation.feedback

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.UserRole
import com.riaanjlagrange.studentschedulerapp.core.data.repository.UsersRepositoryImp
import com.riaanjlagrange.studentschedulerapp.feedback.data.repository.FeedbackRepositoryImpl
import com.riaanjlagrange.studentschedulerapp.feedback.domain.model.FeedbackCategory
import kotlinx.coroutines.launch

class FeedbackViewModel: ViewModel() {
    private val feedbackRepo = FeedbackRepositoryImpl()
    private val usersRepo = UsersRepositoryImp()

    var state by mutableStateOf(FeedbackViewState())
        private set

    fun loadUser() {
        state = state.copy(yourUserIsLoading = true)

        viewModelScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch

            val result = usersRepo.getUserById(userId)

            result.fold(
                ifLeft = { error ->
                    state = state.copy(yourUserError = error.error.message, yourUserIsLoading = false)
                },
                ifRight = { user ->
                    state = state.copy(
                        message = when(user.role) {
                            UserRole.Student -> state.message.copy(student = user)
                            UserRole.Lecturer -> state.message.copy(lecturer = user)
                        },
                        yourUser = user,
                        yourUserIsLoading = false
                    )
                }
            )
        }
    }

    fun setMessage(message: String) {
        state = state.copy(message = state.message.copy(message = message))
    }

    fun setCategory(category: FeedbackCategory) {
        state = state.copy(message = state.message.copy(category = category))
    }

    fun loadSelectedUser(userId: String) {
        state = state.copy(selectedUserIsLoading = true)

        viewModelScope.launch {
            val result = usersRepo.getUserById(userId)

            result.fold(
                ifLeft = { error ->
                    state = state.copy(selectedUserError = error.error.message, selectedUserIsLoading = false)
                },
                ifRight = { user ->
                    state = state.copy(
                        message = when(user.role) {
                            UserRole.Student -> state.message.copy(student = user)
                            UserRole.Lecturer -> state.message.copy(lecturer = user)
                        },
                        selectedUser = user,
                        selectedUserIsLoading = false
                    )
                }
            )
        }
    }

    fun loadMessages(receiverId: String) {
        state = state.copy(isLoading = true)

        viewModelScope.launch {
            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            val result = feedbackRepo.getFeedbackMessages(currentUserId, receiverId)
            result.fold(
                ifLeft = { error ->
                    state = state.copy(error = error.error.message, isLoading = false)
                },
                ifRight = { messages ->
                    state = state.copy(messages = messages, isLoading = false)
                }
            )
        }
    }

    fun sendMessage(receiverId: String) {
        state = state.copy(messageIsLoading = true)
        val message = state.message.copy()

        viewModelScope.launch {
            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
            val result = feedbackRepo.sendFeedback(message)
            result.fold(
                ifLeft = { error ->
                    state = state.copy(messageError = error.error.message, messageIsLoading = false)
                },
                ifRight = { messages ->
                    state = state.copy(messageError = null, isLoading = false)
                }
            )
        }
    }
}
