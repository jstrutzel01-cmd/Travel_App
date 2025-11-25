package com.example.travelapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.data.respository.TravelRepository
import com.example.travelapp.domain.model.Destination
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class HomeViewModel(
    private val repository: TravelRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Initial)
    val uiState : StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun loadAllCountries() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val destinations = repository.getAllCountries()

                val countriesWithSavedStatus = repository.getCountriesWithSavedStatus(destinations)

                _uiState.value = HomeUiState.Success(countriesWithSavedStatus)
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(message = e.message ?: "Unknown error occurred")
            }

        }
    }

    init {
        loadAllCountries()

        viewModelScope.launch {
            searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isBlank()) {
                        loadAllCountries()
                    } else {
                        searchCountryByName(query)
                    }
                }
        }
    }

    private fun searchCountryByName(query: String) {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val destinations = repository.getCountryByName(query)
                _uiState.value = HomeUiState.Success(destinations)
            } catch (e : Exception) {
                _uiState.value = HomeUiState.Error(message = e.message ?: "Unknown error occurred")
            }
        }
    }
    fun onSearchQueryChange(query: String) {
       _searchQuery.value = query

    }

    fun searchCountryByRegion(region: String) {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val destinations = repository.getCountryByRegion(region)
                _uiState.value = HomeUiState.Success(destinations)
            } catch (e : Exception) {
                _uiState.value = HomeUiState.Error(message = e.message ?: "Unknown error occurred")
            }
        }
    }
    fun toggleSaveDestination(destination: Destination) {
        viewModelScope.launch {

            updateDestinationInList(destination.countryCode, !destination.isSaved)

            if (destination.isSaved) {
                repository.unsaveDestination(destination)
            } else {
                repository.saveDestination(destination)
            }
        }
    }

    private fun updateDestinationInList(countryCode: String, newSavedStatus: Boolean) {
        val currentState = _uiState.value
        if (currentState is HomeUiState.Success) {
            val updatedList = currentState.destinations.map { dest ->
                if (dest.countryCode == countryCode) {
                    dest.copy(isSaved = newSavedStatus )
                } else {
                    dest
                }
            }
            _uiState.value = HomeUiState.Success(updatedList)
        }
    }
}