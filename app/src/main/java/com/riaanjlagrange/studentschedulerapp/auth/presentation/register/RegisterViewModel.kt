package com.riaanjlagrange.studentschedulerapp.auth.presentation.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class RegisterViewModel : ViewModel() {
    // set state to register state
    var state = mutableStateOf(RegisterState())
        private set

    // change email state on change
    fun onEmailChange(email: String) {
        state.value = state.value.copy(email = email)
    }

    // change password state on change
    fun onPasswordChange(pass: String) {
        state.value = state.value.copy(password = pass)
    }

    // change confirm password state on change
    fun onConfirmPasswordChange(confirm: String) {
        state.value = state.value.copy(confirmPassword = confirm)
    }

    // register with email and password
    fun register(onResult: (Boolean, String?) -> Unit) {
        if (state.value.password !== state.value.confirmPassword) {
            state.value = state.value.copy(error = "Passwords do not match")
            onResult(false, "Passwords do not match")
            return
        }

        state.value = state.value.copy(isLoading = true, error = null)

        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(state.value.email, state.value.password)
            .addOnCompleteListener { task ->
                state.value = state.value.copy(isLoading = false)
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    state.value = state.value.copy(error = task.exception?.message)
                    onResult(false, task.exception?.message)
                }
            }
    }
}