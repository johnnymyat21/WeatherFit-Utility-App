package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.theme.CP3406_CP5603UtilityAppStarterTemplateTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LinearProgressIndicator
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CP3406_CP5603UtilityAppStarterTemplateTheme {
                UtilityApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UtilityAppPreview() {
    CP3406_CP5603UtilityAppStarterTemplateTheme {
        UtilityApp()
    }
}

@Composable
fun UtilityApp(
    weatherViewModel: WeatherViewModel = viewModel()
) {
    var selectedTab by remember { mutableStateOf("Utility") }
    var useCelsius by remember { mutableStateOf(true) }
    var dailyGoal by remember { mutableIntStateOf(8) }
    val weatherState = weatherViewModel.uiState

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Utility") },
                    label = { Text("Utility") },
                    selected = selectedTab == "Utility",
                    onClick = { selectedTab = "Utility" }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    selected = selectedTab == "Settings",
                    onClick = { selectedTab = "Settings" }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                "Utility" -> UtilityScreen(
                    useCelsius = useCelsius,
                    dailyGoal = dailyGoal,
                    weatherState = weatherState,
                    onRefreshWeather = { weatherViewModel.refreshWeather() }
                )

                "Settings" -> SettingsScreen(
                    useCelsius = useCelsius,
                    dailyGoal = dailyGoal,
                    onUnitChange = { useCelsius = !useCelsius },
                    onIncreaseGoal = { dailyGoal++ },
                    onDecreaseGoal = {
                        if (dailyGoal > 1) {
                            dailyGoal--
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun UtilityScreen(
    useCelsius: Boolean,
    dailyGoal: Int,
    weatherState: WeatherUiState,
    onRefreshWeather: () -> Unit
) {
    var waterCount by remember { mutableIntStateOf(0) }
    val temperatureCelsius = weatherState.temperatureCelsius ?: 29.0
    val displayTemperature = if (useCelsius) {
        temperatureCelsius
    } else {
        temperatureCelsius * 9 / 5 + 32
    }
    val temperatureText = if (useCelsius) {
        "${displayTemperature.toInt()}°C"
    } else {
        "${displayTemperature.toInt()}°F"
    }
    val progress = (waterCount.toFloat() / dailyGoal.toFloat()).coerceIn(0f, 1f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(
            text = "WeatherFit",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "Quick weather and hydration information for your day.",
            style = MaterialTheme.typography.bodyLarge
        )

        ElevatedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Today's Weather",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "Singapore",
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = temperatureText,
                    style = MaterialTheme.typography.headlineLarge
                )

                Text(
                    text = weatherState.condition,
                    style = MaterialTheme.typography.titleMedium
                )

                if (weatherState.errorMessage != null) {
                    Text(
                        text = weatherState.errorMessage,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Button(onClick = onRefreshWeather) {
                    Text("Refresh Weather")
                }
            }
        }

        ElevatedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Hydration Tracker",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "Water cups today: $waterCount / $dailyGoal",
                    style = MaterialTheme.typography.bodyLarge
                )

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = if (waterCount >= dailyGoal) {
                        "Goal completed. Good job!"
                    } else {
                        "Keep going to reach your daily goal."
                    },
                    style = MaterialTheme.typography.bodyMedium
                )

                Button(onClick = { waterCount++ }) {
                    Text("Add Water Cup")
                }

                Button(onClick = { waterCount = 0 }) {
                    Text("Reset")
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(
    useCelsius: Boolean,
    dailyGoal: Int,
    onUnitChange: () -> Unit,
    onIncreaseGoal: () -> Unit,
    onDecreaseGoal: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "Change how the WeatherFit utility app displays information.",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Temperature Unit",
            style = MaterialTheme.typography.titleMedium
        )

        Button(onClick = onUnitChange) {
            Text(if (useCelsius) "Using Celsius °C" else "Using Fahrenheit °F")
        }

        Text(
            text = "Daily Water Goal: $dailyGoal cups",
            style = MaterialTheme.typography.titleMedium
        )

        Button(onClick = onIncreaseGoal) {
            Text("Increase Goal")
        }

        Button(onClick = onDecreaseGoal) {
            Text("Decrease Goal")
        }
    }
}