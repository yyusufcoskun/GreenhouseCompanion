package com.example.greenhousecompanion.ui.elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.greenhousecompanion.R
import com.google.android.material.elevation.ElevationOverlayProvider

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val fontName = GoogleFont("Manrope")

val fontFamily = FontFamily(
    Font(googleFont = fontName, fontProvider = provider)
)
@Composable
fun TextSurface(dataType: String, data:String, symbol:String, width: Int, height: Int){
    Card(
        modifier = Modifier
            .padding(15.dp)
            .size(width = width.dp, height = height.dp),
        border = BorderStroke(2.dp, Color.Transparent),
        shape = RoundedCornerShape(20.dp),
        colors = CardColors(containerColor = Color.White, contentColor = Color.Black, disabledContainerColor = Color.Cyan, disabledContentColor = Color.Blue)
        // contentAlignment = Alignment.Center,
        /*
        elevation = CardDefaults.cardElevation(
                    defaultElevation = 25.dp
       )*/
    ){Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            fontFamily = fontFamily,
            text = "$dataType$data$symbol",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
    }
}
