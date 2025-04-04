package com.example.greenhousecompanion.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.Thermostat
import androidx.compose.material.icons.outlined.ToggleOn
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greenhousecompanion.R
import com.example.greenhousecompanion.ui.elements.GetSensorIcon
import com.example.greenhousecompanion.ui.elements.MySwitch
import com.example.greenhousecompanion.ui.elements.TextSurface
import com.example.greenhousecompanion.ui.elements.fontFamily

// Define navigation items
data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val selectedColor: Color
)

// Define theme colors
data class AppColors(
    val background: Color,
    val cardBackground: Color,
    val primary: Color = Color(0xFF0CA9A1),
    val secondary: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val navigationBarBackground: Color,
    val liveIndicator: Color = Color(0xFF0CA9A1),
    val buttonBackground: Color = Color(0xFFF5F9FC),
    val borderColor: Color = Color(0xFFEFEFEF)
)

val LightThemeColors = AppColors(
    background = Color(0xFFF5F9FC),
    cardBackground = Color.White,
    primary = Color(0xFF0B3954),
    secondary = Color(0xFF616161),
    textPrimary = Color(0xFF212121),
    textSecondary = Color(0xFF616161),
    navigationBarBackground = Color.White,
    liveIndicator = Color(0xFF4CAF50),
    buttonBackground = Color(0xFFF5F9FC),
    borderColor = Color(0xFFEFEFEF)
)

val DarkThemeColors = AppColors(
    background = Color(0xFF1C2130),
    cardBackground = Color(0xFF272D3F),
    primary = Color.White,
    secondary = Color(0xFFBDBDBD),
    textPrimary = Color.White,
    textSecondary = Color(0xFFBDBDBD),
    navigationBarBackground = Color(0xFF1C2130),
    liveIndicator = Color(0xFF4CAF50),
    buttonBackground = Color(0xFF333A4D),
    borderColor = Color(0xFF323950)
)

@Composable
fun GreenhouseCompanionScreen(
    temperature: String = "", 
    humidity: String = "", 
    soilMoisture: String = "",
    onPumpOn: () -> Unit, 
    onPumpOff: () -> Unit,
    onFanOn: () -> Unit, 
    onFanOff: () -> Unit
) {
    // Dark mode state
    var isDarkMode by remember { mutableStateOf(false) }
    val colors = if (isDarkMode) DarkThemeColors else LightThemeColors
    
    // Navigation items
    val navItems = listOf(
        BottomNavItem("Control", Icons.Outlined.ToggleOn, colors.primary),
        BottomNavItem("Data", Icons.Filled.Analytics, colors.primary),
        BottomNavItem("Settings", Icons.Filled.Settings, colors.primary)
    )
    
    // Selected tab state
    var selectedTab by remember { mutableStateOf(0) }
    
    Scaffold(
        containerColor = colors.background,
        bottomBar = {
            NavigationBar(
                containerColor = colors.navigationBarBackground,
                contentColor = colors.primary,
                tonalElevation = 8.dp
            ) {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { 
                            Icon(
                                imageVector = item.icon, 
                                contentDescription = item.title,
                                modifier = Modifier.size(24.dp)
                            ) 
                        },
                        label = { 
                            Text(
                                text = item.title,
                                fontFamily = fontFamily,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            ) 
                        },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = colors.primary,
                            selectedTextColor = colors.primary,
                            indicatorColor = if (isDarkMode) Color(0xFF3A3A3A) else Color(0xFFE5F0F8),
                            unselectedIconColor = colors.textSecondary,
                            unselectedTextColor = colors.textSecondary
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colors.background)
        ) {
            // Only show control screen content for now
            if (selectedTab == 0) {
                ControlScreen(
                    temperature = temperature,
                    humidity = humidity,
                    soilMoisture = soilMoisture,
                    onPumpOn = onPumpOn,
                    onPumpOff = onPumpOff,
                    onFanOn = onFanOn,
                    onFanOff = onFanOff,
                    isDarkMode = isDarkMode,
                    onThemeToggle = { isDarkMode = !isDarkMode },
                    colors = colors
                )
            } else {
                // Placeholder for other screens
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (selectedTab == 1) "Data Screen - Coming Soon" else "Settings Screen - Coming Soon",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = colors.textPrimary
                    )
                }
            }
        }
    }
}

@Composable
fun LiveIndicator(colors: AppColors) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(colors.liveIndicator.copy(alpha = 0.15f))
            .padding(horizontal = 10.dp, vertical = 3.dp)
    ) {
        Text(
            text = "Live",
            color = colors.liveIndicator,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp
        )
    }
}

@Composable
fun ConfigureButton(
    onClick: () -> Unit,
    colors: AppColors
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.buttonBackground,
            contentColor = colors.primary
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = "Configure",
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    }
}

@Composable
fun ControlScreen(
    temperature: String,
    humidity: String,
    soilMoisture: String,
    onPumpOn: () -> Unit,
    onPumpOff: () -> Unit,
    onFanOn: () -> Unit,
    onFanOff: () -> Unit,
    isDarkMode: Boolean,
    onThemeToggle: () -> Unit,
    colors: AppColors
) {
    // Convert string values to float for progress bars (capped at 100)
    val humidityValue = humidity.replace("%", "").toFloatOrNull()?.div(100f) ?: 0.5f
    val soilValue = soilMoisture.replace("%", "").toFloatOrNull()?.div(100f) ?: 0.5f
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top bar with dark mode toggle and app title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Dark mode toggle
            IconButton(
                onClick = onThemeToggle,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = if (isDarkMode) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                    contentDescription = "Toggle Dark Mode",
                    tint = colors.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            // App title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.waterdrop2),
                    contentDescription = "Greenhouse Icon",
                    tint = colors.primary,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "GREENHOUSE",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    letterSpacing = 1.sp,
                    color = colors.primary
                )
            }
            
            // Empty spacer for balance
            Spacer(modifier = Modifier.size(40.dp))
        }
        
        // Sensor readings section
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = colors.cardBackground
            ),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            ),
            border = BorderStroke(1.dp, colors.borderColor)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Header with title and Live indicator
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sensor Readings",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.textPrimary,
                        fontFamily = fontFamily
                    )
                    
                    LiveIndicator(colors)
                }
                
                // Subtitle
                Text(
                    text = "Current greenhouse conditions",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = colors.textSecondary,
                    modifier = Modifier.padding(bottom = 12.dp),
                    fontFamily = fontFamily
                )
                
                // Sensor readings
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    TextSurface(
                        dataType = "Temperature", 
                        temperature, "°C", 150, 110,
                        isDarkMode = isDarkMode,
                        icon = Icons.Outlined.Thermostat,
                        showProgressBar = false
                    )
                    
                    TextSurface(
                        dataType = "Humidity", 
                        humidity, "%", 150, 110,
                        isDarkMode = isDarkMode,
                        icon = Icons.Outlined.WaterDrop,
                        progressValue = humidityValue
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Soil moisture
                TextSurface(
                    dataType = "Soil Moisture", 
                    soilMoisture, "%", 310, 110,
                    isDarkMode = isDarkMode,
                    icon = Icons.Filled.Opacity,
                    progressValue = soilValue
                )
            }
        }
        
        // Controls section
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = colors.cardBackground
            ),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            ),
            border = BorderStroke(1.dp, colors.borderColor)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Controls",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary,
                    fontFamily = fontFamily
                )
                
                Text(
                    text = "Manage your greenhouse systems",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = colors.textSecondary,
                    modifier = Modifier.padding(bottom = 12.dp),
                    fontFamily = fontFamily
                )
                
                // Container for controls
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Irrigation System Switch
                    val waterDropIcon: Painter = painterResource(id = R.drawable.waterdrop2)
                    MySwitch(
                        waterDropIcon, 18,
                        168f, 0.7f, 1f,
                        168f, 0.5f, 0.5f,
                        onPumpOn, onPumpOff,
                        isDarkMode = isDarkMode,
                        title = "Irrigation System"
                    )
                    
                    // Ventilation Switch
                    val fanIcon: Painter = painterResource(id = R.drawable.fan)
                    MySwitch(
                        fanIcon, 15,
                        202f, 0.17f, 1f,
                        19f, 0.17f, 0.5f,
                        onFanOn, onFanOff,
                        isDarkMode = isDarkMode,
                        title = "Ventilation"
                    )
                }
            }
        }
    }
}