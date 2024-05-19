package com.example.greenhousecompanion.ui.elements

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MySwitch(icon: Painter, iconSize: Int,
             hueThumb: Float, saturationThumb: Float, lightnessThumb: Float,
             hueTrack: Float, saturationTrack: Float, lightnessTrack: Float,
             onPostRequestOn: () -> Unit, onPostRequestOff: () -> Unit) {
    var checked by remember { mutableStateOf(false) }

    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
            if (checked) {
                println("Switch ON.")
                onPostRequestOn()
            } else {
                println("Switch OFF.")
                onPostRequestOff()
            }

        },
        modifier = Modifier
            .scale(2.5f)
            .padding(25.dp),
        thumbContent = {
            Image(painter = icon, contentDescription = "Icon", modifier = Modifier.size(iconSize.dp))
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.hsl(hueThumb, saturationThumb, lightnessThumb), //(168f, 0.70f, 0.5f)
            checkedTrackColor = Color.hsl(hueTrack, saturationTrack, lightnessTrack), // (168f, 0.5f, 0.5f
            uncheckedTrackColor = Color.Gray
            )
    )

}