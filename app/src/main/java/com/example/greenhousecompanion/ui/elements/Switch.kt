package com.example.greenhousecompanion.ui.elements

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greenhousecompanion.R
import androidx.compose.foundation.BorderStroke

// Set up custom font for consistency
private val switchFontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

private val switchInterFont = GoogleFont("Inter")

private val switchFontFamily = FontFamily(
    Font(googleFont = switchInterFont, fontProvider = switchFontProvider)
)

@Composable
fun MySwitch(
    icon: Painter, 
    iconSize: Int,
    hueThumb: Float, 
    saturationThumb: Float, 
    lightnessThumb: Float,
    hueTrack: Float, 
    saturationTrack: Float, 
    lightnessTrack: Float,
    onPostRequestOn: () -> Unit, 
    onPostRequestOff: () -> Unit,
    isDarkMode: Boolean = false,
    title: String = if (iconSize == 18) "Irrigation System" else "Ventilation"
) {
    var checked by remember { mutableStateOf(false) }
    
    // Colors
    val blueColor = Color(0xFF0CA9A1) // Same teal color used throughout the app
    val cardColor = if (isDarkMode) Color(0xFF272D3F) else Color.White
    val textColor = if (isDarkMode) Color(0xFFE0E0E0) else Color(0xFF333333)
    val secondaryTextColor = if (isDarkMode) Color(0xFFBDBDBD) else Color(0xFF757575)
    val iconBgColor = if (isDarkMode) {
        if (checked) Color(0xFF054D49) else Color(0xFF33393D)
    } else {
        if (checked) Color(0xFFD1F0EE) else Color(0xFFF0F0F0) // Light teal for active
    }
    val borderColor = if (isDarkMode) Color(0xFF323950) else Color(0xFFEEEEEE)
    val switchTrackColor = blueColor // Same teal in both dark and light mode
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
        ) {
            // Left side with icon and text
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Icon in circle
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(iconBgColor),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = icon, 
                        contentDescription = null, 
                        modifier = Modifier.size(iconSize.dp),
                        colorFilter = ColorFilter.tint(if (checked) blueColor else secondaryTextColor)
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                // Title and status
                Column {
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = textColor,
                        fontFamily = switchFontFamily
                    )
                    Text(
                        text = if (checked) "Active" else "Inactive",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = secondaryTextColor,
                        fontFamily = switchFontFamily
                    )
                }
            }
            
            // Switch control - pushed to the very right
            Box(
                modifier = Modifier.width(48.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Switch(
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                        if (checked) {
                            onPostRequestOn()
                        } else {
                            onPostRequestOff()
                        }
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = switchTrackColor,
                        uncheckedTrackColor = if (isDarkMode) Color(0xFF505668) else Color.LightGray,
                        uncheckedThumbColor = Color.White,
                        uncheckedBorderColor = if (isDarkMode) Color(0xFF505668) else Color.LightGray
                    ),
                    modifier = Modifier.scale(0.85f)
                )
            }
        }
    }
}