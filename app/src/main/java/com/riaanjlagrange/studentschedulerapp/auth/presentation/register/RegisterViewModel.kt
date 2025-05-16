package com.riaanjlagrange.studentschedulerapp.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class RegisterViewModel : ViewModel() {
    // set state to register state
    var state by mutableStateOf(RegisterState())
        private set

    // change email state on change
    fun onEmailChange(email: String) {
        state = state.copy(email = email)
    }

    // change password state on change
    fun onPasswordChange(pass: String) {
        state = state.copy(password = pass)
    }

    // change confirm password state on change
    fun onConfirmPasswordChange(confirm: String) {
        state = state.copy(confirmPassword = confirm)
    }

    // register with email and password
    fun register(onResult: (Boolean, String?) -> Unit) {
        if (state.password !== state.confirmPassword) {
            state = state.copy(error = "Passwords do not match")
            onResult(false, "Passwords do not match")
            return
        }

        // set isLoading to true and error to null
        state = state.copy(isLoading = true, error = null)

        FirebaseAuth.getInstance()
            // try to create the user with email and password
            .createUserWithEmailAndPassword(state.email, state.password)
            // check if registration was successfull or not
            .addOnCompleteListener { task ->
                state = state.copy(isLoading = false)
                if (task.isSuccessful) {
                    // set result to true
                    onResult(true, null)
                } else {
                    // set error and loading state if there is an error
                    state = state.copy(error = task.exception?.message)
                    onResult(false, task.exception?.message)
                }
            }
    }
}