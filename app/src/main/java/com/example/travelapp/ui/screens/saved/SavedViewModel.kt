package com.example.travelapp.ui.screens.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.data.respository.TravelRepository
import com.example.travelapp.domain.model.Destination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SavedViewModel(
    private val repository: TravelRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SavedUiState>(SavedUiState.Empty)
    val uiState : StateFlow<SavedUiState> = _uiState.asStateFlow()

    private fun loadSavedDestinations() {
       viewModelScope.launch {
           repository.getSavedDestinations().collect { destinations ->
               if (destinations.isEmpty()) {
                   _uiState.value = SavedUiState.Empty
               } else {
                   _uiState.value = SavedUiState.Success(destinations)
               }
           }
       }
    }
    init {
        loadSavedDestinations()
    }

    fun unsaveDestination(destination: Destination) {
        viewModelScope.launch {
            repository.unsaveDestination(destination)
        }
    }
}