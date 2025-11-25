package com.example.travelapp.ui.screens.saved

import com.example.travelapp.domain.model.Destination

sealed class SavedUiState {

    data object Initial: SavedUiState()

    data object Loading: SavedUiState()

    data class Success (
        val savedDestinations : List<Destination>
    ) : SavedUiState()

    data class Error (
        val message: String
    ) : SavedUiState()

}