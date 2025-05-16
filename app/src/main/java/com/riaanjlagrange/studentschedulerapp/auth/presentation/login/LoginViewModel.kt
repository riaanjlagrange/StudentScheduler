package com.riaanjlagrange.studentschedulerapp.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    // set state to login state
    var state by mutableStateOf(LoginViewState())
        private set

    // change email state on change
    fun onEmailChange(newEmail: String) {
        state = state.copy(email = newEmail)
    }

    // change password state on change
    fun onPasswordChange(newPassword: String) {
        state = state.copy(password = newPassword)
    }

    // login with email and password
    fun login(onResult: (Boolean, String?) -> Unit) {
        // set isLoading to true, and error to null
        state = state.copy(isLoading = true, error = null)

        FirebaseAuth.getInstance()
            // try to sign in with email and password
            .signInWithEmailAndPassword(state.email, state.password)
            // check if login was successful or not
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // set the result to true
                    onResult(true, null)
                } else {
                    // set error and loading state if there is an error
                    state = state.copy(error = task.exception?.message, isLoading = false)
                    onResult(false, task.exception?.message)
                }
            }
    }
}