package com.example.greenhousecompanion.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MyButton(onClick: () -> Unit) {
    Button(
        onClick = {
            onClick()
            println("Hello, world!")
        },
        modifier = Modifier.padding(30.dp)
    ) {
        Text("Click Me")
    }
}
