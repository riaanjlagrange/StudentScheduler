package com.riaanjlagrange.studentschedulerapp.auth.presentation.register

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.riaanjlagrange.studentschedulerapp.R
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.UserRole
import com.riaanjlagrange.studentschedulerapp.auth.presentation.components.AuthButton
import com.riaanjlagrange.studentschedulerapp.utils.components.Header
import com.riaanjlagrange.studentschedulerapp.auth.presentation.components.AuthTextField
import com.riaanjlagrange.studentschedulerapp.utils.components.ErrorText

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = viewModel()
) {
    // get state from viewModel's state
    val state = viewModel.state
    val context = LocalContext.current

    // column to vertically display composables
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // set register header
        Header("Register")

        Spacer(modifier = Modifier.height(16.dp))

        // set register text fields of name, email, password and confirm password
        AuthTextField(state.name, viewModel::onNameChange, "Name")
        Spacer(modifier = Modifier.height(8.dp))
        AuthTextField(state.email, viewModel::onEmailChange, "Email")
        Spacer(modifier = Modifier.height(8.dp))
        AuthTextField(state.password, viewModel::onPasswordChange, "Password", isPassword = true)
        Spacer(modifier = Modifier.height(8.dp))
        AuthTextField(state.confirmPassword, viewModel::onConfirmPasswordChange, "Confirm Password", isPassword = true)
        Text(
            text = "Password should be at least 6 characters",
            fontSize = 12.sp,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // set auth button
        AuthButton(
            onClick = {
                viewModel.register {
                    Toast
                        .makeText(context, "Registration Successful!", Toast.LENGTH_SHORT)
                        .show()
                    navController.navigate("login/${UserRole.Student}")
                }
            },
            isLoading = state.isLoading,
            text = stringResource(R.string.register_button)
        )

        // set error state if not null
        ErrorText(state.error)

        Spacer(modifier = Modifier.height(8.dp))

        AuthButton(
            onClick = {
                navController.navigate("role_select")
            },
            isLoading = false,
            text = "Login Instead"
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen(navController = rememberNavController())
}