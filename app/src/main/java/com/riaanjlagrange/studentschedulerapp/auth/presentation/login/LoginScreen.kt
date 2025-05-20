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
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.UserRole
import com.riaanjlagrange.studentschedulerapp.auth.presentation.components.AuthButton
import com.riaanjlagrange.studentschedulerapp.utils.components.Header
import com.riaanjlagrange.studentschedulerapp.auth.presentation.components.AuthTextField
import com.riaanjlagrange.studentschedulerapp.utils.components.ErrorText

@Composable
fun LoginScreen(
    navController: NavController,
    selectedRole: UserRole,
    viewModel: LoginViewModel = viewModel()
) {
    // get state from LoginViewModel's state
    val state = viewModel.state
    val context = LocalContext.current

    // check if user is already signed in or not
    val user = FirebaseAuth.getInstance().currentUser
    LaunchedEffect(user) {
        if (user != null) {
            navController.navigate("view_bookings") {
                popUpTo("login/${selectedRole.name}") { inclusive = true }
            }
        }
    }

    // column to vertically display composables
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        // set login header
        Header("${selectedRole.name.lowercase().replaceFirstChar { it.uppercase() }} Login")


        Spacer(modifier = Modifier.height(16.dp))

        // set login text fields for email and password
        AuthTextField(state.email, viewModel::onEmailChange, "Email")
        Spacer(modifier = Modifier.height(8.dp))
        AuthTextField(state.password, viewModel::onPasswordChange, "Password", isPassword = true)

        Spacer(modifier = Modifier.height(16.dp))

        // set auth button
        AuthButton(
            onClick = {
                viewModel.login { user ->
                    if (user.role != selectedRole) {
                        Toast.makeText(context, "Account not registered as ${selectedRole.name}", Toast.LENGTH_LONG).show()
                        return@login
                    }

                    Toast.makeText(context, "Welcome ${user.role.name}", Toast.LENGTH_SHORT).show()
                    navController.navigate("view_bookings") {
                        popUpTo(0)
                        launchSingleTop = true
                    }
                    //navController.navigate("calendar")
                }
            },
            isLoading = state.isLoading,
            text = "Login"
        )

        // set error state if not null
        ErrorText(state.error)

        Spacer(modifier = Modifier.height(8.dp))

        Text("No account?")

        Spacer(modifier = Modifier.height(4.dp))

        AuthButton(
            onClick = {
                navController.navigate("register")
            },
            isLoading = false,
            text = "Create New Account"
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        navController = rememberNavController(),
        selectedRole = UserRole.Student,
    )
}
