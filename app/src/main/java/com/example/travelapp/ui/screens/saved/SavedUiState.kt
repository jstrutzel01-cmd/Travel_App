package com.example.travelapp.ui.screens.saved

import com.example.travelapp.domain.model.Destination

sealed class SavedUiState {

    data object Empty: SavedUiState()

    data object Loading: SavedUiState()

    data class Success (
        val destinations : List<Destination>
    ) : SavedUiState()

    data class Error (
        val message: String
    ) : SavedUiState()

}