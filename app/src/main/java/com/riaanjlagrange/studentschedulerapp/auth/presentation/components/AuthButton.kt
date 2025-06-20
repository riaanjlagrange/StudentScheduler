package com.riaanjlagrange.studentschedulerapp.auth.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.riaanjlagrange.studentschedulerapp.R

@Composable
fun AuthButton(
    onClick: () -> Unit,
    isLoading: Boolean,
    text: String
) {
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors()) {
        if (isLoading) {
            // show loading indicator
            CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp)
        } else {
            Text(text)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAuthButton() {
   AuthButton(
       onClick = {},
       isLoading = false,
       text = stringResource(R.string.default_button) // auth
   )
}