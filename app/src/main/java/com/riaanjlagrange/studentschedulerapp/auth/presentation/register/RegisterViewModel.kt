package com.riaanjlagrange.studentschedulerapp.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riaanjlagrange.studentschedulerapp.auth.data.repository.AuthRepositoryImpl
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.AuthUser
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.UserRole
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {
    private var repo = AuthRepositoryImpl()

    var state by mutableStateOf(RegisterViewState())
        private set


    fun onEmailChange(email: String) {
        state = state.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        state = state.copy(password= password)
    }

    fun onConfirmPasswordChange(confirm: String) {
        state = state.copy(confirmPassword = confirm)
    }

    fun onRoleSelected(role: UserRole) {
        state = state.copy(role = role)
    }

    fun register(onSuccess: (AuthUser) -> Unit) {
        state = state.copy(isLoading = true, error = null)

        viewModelScope.launch {
            val result = repo.register(
                email = state.email,
                password = state.password,
                confirmPassword = state.confirmPassword,
                role = state.role
            )

            result.fold(
                ifLeft = { error ->
                    state = state.copy(isLoading = false, error = error.error.message)
                },
                ifRight = { user ->
                    state = state.copy(isLoading = false, error = null)
                    onSuccess(user)
                    // it should return the authUser to be used
                }
            )
        }
    }
}
