# WeatherFit Utility App

WeatherFit is a simple Android utility app created for CP3406 / CP5307 Assessment 1.  
The app provides quick at-a-glance weather and hydration information for daily use.

## App Purpose

The purpose of WeatherFit is to help users quickly check the current weather in Singapore and track their daily water intake. The app is designed as a utility app because it focuses on simple, fast, and useful daily-life information.

## Core Features

- Displays current Singapore weather
- Fetches live weather data using the Open-Meteo API
- Shows temperature in Celsius or Fahrenheit
- Displays a short weather condition
- Includes a hydration tracker
- Shows hydration progress using a progress bar
- Allows the user to increase or decrease their daily water goal
- Includes a refresh button to update weather data
- Uses bottom navigation between Utility and Settings screens

## Screens

### Utility Screen

The Utility screen displays:

- Current location
- Current temperature
- Weather condition
- Refresh Weather button
- Hydration tracker
- Water progress bar
- Add Water Cup and Reset buttons

### Settings Screen

The Settings screen allows the user to:

- Switch between Celsius and Fahrenheit
- Increase or decrease the daily water goal

## Technical Implementation

This app was built using:

- Kotlin
- Android Studio
- Jetpack Compose
- Material Design 3
- ViewModel
- Repository pattern
- Retrofit
- Gson converter
- Open-Meteo weather API

## Architecture

The app uses a simple architecture:

- `MainActivity.kt` contains the main Jetpack Compose UI.
- `WeatherViewModel.kt` manages UI state and weather refresh logic.
- `WeatherRepository.kt` handles API communication.
- `WeatherApi.kt` defines the Retrofit API interface.
- `WeatherResponse.kt` stores the weather API response data.

## API

WeatherFit uses the Open-Meteo API to retrieve current weather information for Singapore.

## How to Run

1. Open the project in Android Studio.
2. Wait for Gradle sync to finish.
3. Select an emulator or physical Android device.
4. Click Run.
5. Use the Utility and Settings tabs to test the app.

## Assessment Notes

This project demonstrates a utility-style Android application with a main screen, settings screen, Jetpack Compose UI, Material Design 3 components, ViewModel, Repository pattern, and networking using Retrofit.

## Reflection

A separate self-reflection document is submitted with the assignment.