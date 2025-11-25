package com.example.travelapp.ui.screens.home

import com.example.travelapp.domain.model.Destination

sealed class HomeUiState {
    data object Initial: HomeUiState()

    data object Loading: HomeUiState()

    data class Success(
        val destinations: List<Destination>
    ) : HomeUiState()

    data class Error(
        val message: String
    ): HomeUiState()
}