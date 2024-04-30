package com.example.greenhousecompanion.ui.elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.material.elevation.ElevationOverlayProvider


@Composable
fun TextSurface(data:String){
    Card(
        modifier = Modifier
            .padding(15.dp)
            .size(width=90.dp, height =350.dp),
        border = BorderStroke(2.dp, Color.White),
        shape = RoundedCornerShape(20.dp),
        // contentAlignment = Alignment.Center,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 25.dp
        )
    ){
        Text("Hello $data", modifier = Modifier.padding(30.dp))
    }
}
