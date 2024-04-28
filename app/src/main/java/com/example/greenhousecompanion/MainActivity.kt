package com.example.greenhousecompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.greenhousecompanion.screens.GreenhouseCompanionScreen
import com.example.greenhousecompanion.ui.MyButton
import com.example.greenhousecompanion.ui.MySwitch
import com.example.greenhousecompanion.ui.TextSurface

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreenhouseCompanionScreen()
        }
    }
}

