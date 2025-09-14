package com.example.trendy10.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun NoInternetDialog(onRetry: () -> Unit) {
    AlertDialog(
        onDismissRequest = {  },
        title = {
            Text(text = "No Internet Connection")
        },
        text = {
            Text(text = "Please check your internet connection and try again.")
        },
        confirmButton = {
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    )
}
