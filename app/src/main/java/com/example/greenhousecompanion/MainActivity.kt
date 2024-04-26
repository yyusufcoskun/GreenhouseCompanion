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
import com.example.greenhousecompanion.ui.MyButton
import com.example.greenhousecompanion.ui.MySwitch
import com.example.greenhousecompanion.ui.TextSurface

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           /* GreenhouseCompanionTheme{
                colors = MaterialTheme.colorScheme.background.White
            }*/
            Column {
                Row {
                    val test = "test"
                    val test2 = "test"
                    val test3 = "test"
                    TextSurface(test)
                    TextSurface(test2)
                    TextSurface(test3)
                }
                Row {
                    MyButton() {
                        println("Button1 clicked")
                    }
                    MyButton {
                        println("Button2 clicked")
                    }
                }
                Row{
                    MySwitch()
                }
            }
        }
    }
}

