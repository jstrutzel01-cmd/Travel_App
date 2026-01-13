# Travel App

Browse countries and save your favorites.

## Features

- Search any country
- Save places you want to visit
- Works offline for saved items
- Material 3 UI

## Built With

Kotlin, Compose, Retrofit, Room. Standard Android stack.

## How It Works

Pulls data from REST Countries API. When you save something, it goes into a local Room database. The saved screen auto-updates when you add/remove favorites using Flow.

## Architecture

Went with MVVM + Repository pattern:
- ViewModels manage UI state
- Repository handles data (API + database)
- DTOs convert to cleaner domain models for the UI

## Running It

Open in Android Studio and hit run. No API key needed.

## Things I'd Add

- Images (flag URLs are in the API)
- Region filters
- Population sorting
- Better error messages
- Tests
