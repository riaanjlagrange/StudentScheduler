package com.riaanjlagrange.studentschedulerapp.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riaanjlagrange.studentschedulerapp.auth.data.repository.AuthRepositoryImpl
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.AuthUser
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val repo = AuthRepositoryImpl()

    var state by mutableStateOf(LoginViewState())
        private set

    fun onEmailChange(email: String) {
        state = state.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        state = state.copy(password = password)
    }

    fun login(onSuccess: (AuthUser) -> Unit) {
        state = state.copy(isLoading = true, error = null)

        viewModelScope.launch {
            val result = repo.login(state.email, state.password)

            result.fold(
                ifLeft = { error ->
                    state = state.copy(isLoading = false, error = error.error.message)
                },
                ifRight = { user ->
                    state = state.copy(isLoading = false, error = null)
                    onSuccess(user)
                }
            )
        }
    }
}
