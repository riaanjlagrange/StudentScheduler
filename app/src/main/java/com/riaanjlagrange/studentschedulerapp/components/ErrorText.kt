package com.riaanjlagrange.studentschedulerapp.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.riaanjlagrange.studentschedulerapp.R

// global error component to use for all errors
@Composable
fun ErrorText(error: String?) {
    // only shows error if error is not null
    if (!error.isNullOrEmpty()) {
        Text(
            text = error.toString(),
            color = Color.Red,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewErrorText() {
    ErrorText(stringResource(R.string.default_error)) // Something has gone wrong
}