package com.example.greenhousecompanion.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp

@Composable
fun MyButton(onClick: () -> Unit) {
    Button(
        onClick = {
            onClick()
            println("Hello, world!")
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Cyan
        ),
        modifier = Modifier.padding(30.dp)
    ) {
        Text("buttonFunc")
    }
}

// , buttonFunc : String
