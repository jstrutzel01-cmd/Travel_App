package com.example.travelapp.ui.screens.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.travelapp.data.respository.TravelRepository

class SavedViewModelFactory(
    private val repository: TravelRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SavedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}