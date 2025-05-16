package com.riaanjlagrange.studentschedulerapp.auth.presentation.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.riaanjlagrange.studentschedulerapp.auth.presentation.components.AuthButton
import com.riaanjlagrange.studentschedulerapp.components.Header
import com.riaanjlagrange.studentschedulerapp.auth.presentation.components.AuthTextField
import com.riaanjlagrange.studentschedulerapp.components.ErrorText

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {
    // get state from LoginViewModel's state
    val state = viewModel.state
    val context = LocalContext.current

    // column to vertically display composables
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // set login header
        Header("Login")

        Spacer(modifier = Modifier.height(16.dp))

        // set login text fields for email and password
        AuthTextField(state.email, viewModel::onEmailChange, "Email")
        Spacer(modifier = Modifier.height(8.dp))
        AuthTextField(state.password, viewModel::onEmailChange, "Password", isPassword = true)

        Spacer(modifier = Modifier.height(16.dp))

        // set auth button
        AuthButton(
            onClick = {
                // set onResult to success or error
                viewModel.login { success, error ->
                    if (success) {
                        // navigate to route after success
                        navController.navigate("dashboard") // need to change to real route
                    } else {
                        Toast.makeText(context, error ?: "Login failed", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            isLoading = state.isLoading,
            text = "Login"
        )


        // set error state if not null
        ErrorText(state.error)
    }

}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}
