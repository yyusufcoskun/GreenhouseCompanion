package com.example.greenhousecompanion.ui.elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Thermostat
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greenhousecompanion.R
import kotlin.math.min

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val fontName = GoogleFont("Inter")

val fontFamily = FontFamily(
    Font(googleFont = fontName, fontProvider = provider)
)

@Composable
fun TextSurface(
    dataType: String, 
    data: String, 
    symbol: String, 
    width: Int, 
    height: Int,
    isDarkMode: Boolean = false,
    icon: ImageVector? = null,
    painter: Painter? = null,
    progressValue: Float = 0.5f,
    showProgressBar: Boolean = true
) {
    val backgroundColor = if (isDarkMode) Color(0xFF272D3F) else Color.White
    val borderColor = if (isDarkMode) Color(0xFF323950) else Color(0xFFEEEEEE)
    val labelColor = if (isDarkMode) Color(0xFFBDBDBD) else Color(0xFF757575)
    val valueColor = Color(0xFF0CA9A1) // Same teal for both light and dark mode
    val progressBarColor = valueColor // Use the same teal color for progress bars in both modes
    val progressTrackColor = if (isDarkMode) Color(0xFF303855) else Color(0xFFEEEEEE)
    
    // Ensure progress is between 0 and 1
    val normalizedProgress = min(1f, progressValue.coerceAtLeast(0f))
    
    Card(
        modifier = Modifier
            .padding(4.dp)
            .size(width = width.dp, height = height.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        border = BorderStroke(1.dp, borderColor)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icon
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = labelColor,
                        modifier = Modifier.size(22.dp)
                    )
                } else if (painter != null) {
                    Icon(
                        painter = painter,
                        contentDescription = null,
                        tint = labelColor,
                        modifier = Modifier.size(22.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Sensor type label
                Text(
                    text = dataType,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    color = labelColor,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Value display depends on the reading type
                if (dataType.contains("Temperature", ignoreCase = true)) {
                    // For temperature, show value and unit side by side
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = data,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = valueColor,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = symbol,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = valueColor,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 1.dp)
                        )
                    }
                } else {
                    // For humidity and soil moisture, show value with % vertically aligned
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = data,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = valueColor,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = symbol,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = valueColor,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 1.dp)
                        )
                    }
                }
                
                // Only show progress bar if showProgressBar is true
                if (showProgressBar) {
                    Spacer(modifier = Modifier.height(10.dp))
                    
                    // Progress bar
                    LinearProgressIndicator(
                        progress = normalizedProgress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        color = progressBarColor, // Black in dark mode, teal in light mode
                        trackColor = progressTrackColor,
                        strokeCap = StrokeCap.Round
                    )
                }
            }
        }
    }
}

@Composable
fun GetSensorIcon(dataType: String): ImageVector {
    return when {
        dataType.contains("Temperature", ignoreCase = true) -> Icons.Outlined.Thermostat
        dataType.contains("Humidity", ignoreCase = true) -> Icons.Outlined.WaterDrop
        dataType.contains("Soil", ignoreCase = true) -> Icons.Outlined.WaterDrop
        else -> Icons.Outlined.Thermostat
    }
}
