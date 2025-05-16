package com.riaanjlagrange.studentschedulerapp.utils.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.riaanjlagrange.studentschedulerapp.R

@Composable
fun Header(title: String) {
    Text(
        text = title,
        fontSize = 22.sp,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewHeader() {
    Header(stringResource(R.string.default_text)) // auth
}