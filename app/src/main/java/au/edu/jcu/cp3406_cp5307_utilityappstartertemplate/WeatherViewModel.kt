package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.WeatherRepository
import kotlinx.coroutines.launch

data class WeatherUiState(
    val isLoading: Boolean = false,
    val temperatureCelsius: Double? = null,
    val condition: String = "Loading weather...",
    val errorMessage: String? = null
)

class WeatherViewModel(
    private val repository: WeatherRepository = WeatherRepository()
) : ViewModel() {

    var uiState by mutableStateOf(WeatherUiState(isLoading = true))
        private set

    init {
        refreshWeather()
    }

    fun refreshWeather() {
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val response = repository.getSingaporeWeather()

                uiState = WeatherUiState(
                    isLoading = false,
                    temperatureCelsius = response.current.temperature,
                    condition = repository.getWeatherDescription(response.current.weatherCode),
                    errorMessage = null
                )
            } catch (e: Exception) {
                uiState = WeatherUiState(
                    isLoading = false,
                    temperatureCelsius = 29.0,
                    condition = "Mostly Clear",
                    errorMessage = "Live weather unavailable. Showing fallback data."
                )
            }
        }
    }
}

