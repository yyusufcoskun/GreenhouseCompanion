package com.example.greenhousecompanion.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.greenhousecompanion.R
import com.example.greenhousecompanion.ui.elements.MySwitch
import com.example.greenhousecompanion.ui.elements.TextSurface

@Composable
fun GreenhouseCompanionScreen(){
    MaterialTheme{
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center, // Yukarı-aşağı doğrultusunda ekranın ortasına getirme
            horizontalAlignment = Alignment.CenterHorizontally // Sağ-sol doğrultusunda ekranın ortasına getirme
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, // Row yüksekliğinin ortasına getiriyor
                horizontalArrangement = Arrangement.Center
            ){
                val test = "test"
                val test2 = "test"
                val test3 = "test"
                TextSurface(test)
                TextSurface(test2)
                TextSurface(test3)
            }
            Spacer(modifier = Modifier.size(30.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                val waterDropIcon: Painter = painterResource(id = R.drawable.waterdrop2) // without file extension name
                MySwitch(waterDropIcon, 18,
                    168f, 0.7f, 1f,
                    168f, 0.5f, 0.5f)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                val fanIcon: Painter = painterResource(id = R.drawable.fan) // without file extension name
                MySwitch(fanIcon, 15,
                    202f, 0.17f, 1f,
                    19f, 0.17f, 0.5f)
            }
            Spacer(modifier = Modifier.size(60.dp))
        }
    }
}