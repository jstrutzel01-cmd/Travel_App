package com.example.travelapp.ui.screens.saved

import androidx.lifecycle.ViewModel
import com.example.travelapp.data.respository.TravelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SavedViewModel(
    private val repository: TravelRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SavedUiState>(SavedUiState.Initial)
    val uiState : StateFlow<SavedUiState> = _uiState.asStateFlow()

    fun getSavedDestinations() {
       _uiState.value = SavedUiState.Loading
       try {
           val savedDestinations = repository.getSavedDestinations()
           _uiState.value = SavedUiState.Success(savedDestinations)
       } catch(e : Exception) {
            _uiState.value = SavedUiState.Error(e.message ?: "Unknown Error")
       }
    }
}