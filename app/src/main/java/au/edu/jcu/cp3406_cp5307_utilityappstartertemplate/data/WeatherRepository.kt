package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository {

    private val weatherApi: WeatherApi = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApi::class.java)

    suspend fun getSingaporeWeather(): WeatherResponse {
        return weatherApi.getCurrentWeather(
            latitude = 1.3521,
            longitude = 103.8198
        )
    }

    fun getWeatherDescription(weatherCode: Int): String {
        return when (weatherCode) {
            0 -> "Clear Sky"
            1, 2, 3 -> "Partly Cloudy"
            45, 48 -> "Foggy"
            51, 53, 55 -> "Drizzle"
            61, 63, 65 -> "Rainy"
            80, 81, 82 -> "Rain Showers"
            95 -> "Thunderstorm"
            else -> "Unknown Weather"
        }
    }
}