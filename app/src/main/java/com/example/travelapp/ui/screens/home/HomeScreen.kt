package com.example.travelapp.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travelapp.data.local.database.TravelDatabase
import com.example.travelapp.data.respository.TravelRepository
import com.example.travelapp.domain.model.Destination

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(
            repository = TravelRepository(
                dao = TravelDatabase.getInstance(LocalContext.current).destinationDao()
            )
        )
    ),
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery = viewModel.searchQuery.collectAsStateWithLifecycle()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text(
            text = "Travel Destinations",
            style = MaterialTheme.typography.headlineSmall
        )
        TextField(
            value = searchQuery.value,
            onValueChange = {viewModel.onSearchQueryChange(it)},
            placeholder = {Text("Search Countries")}

        )


        when (val state = uiState.value) {
            is HomeUiState.Initial -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Search for a country or region",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            is HomeUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is HomeUiState.Success -> {
                if (state.destinations.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No destinations found")
                    }
                } else {
                    Text(
                        text = "Found ${state.destinations.size} countries",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    LazyColumn {
                        items(state.destinations) { destination ->
                            DestinationCard(
                                destination = destination,
                                onSavedClick = {destination ->
                                    viewModel.toggleSaveDestination(destination)
                                }
                            )
                        }
                    }
                }
            }
            is HomeUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Text(
                            text = "Error"
                        )
                        Text(
                            text = state.message
                        )
                    }
                }
            }
        }
    }



}

@Composable
fun DestinationCard(
    destination: Destination,
    onSavedClick: (Destination) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Text(
                    text = destination.name,
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(onClick = {onSavedClick(destination)}) {
                    Icon(
                        imageVector = if (destination.isSaved) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Outlined.FavoriteBorder
                        },
                        contentDescription = if (destination.isSaved) "Unsave" else "Save"
                    )
                }
            }
            Text(
                text = "Capital: ${destination.capital}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Region: ${destination.region}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Population: ${destination.population}",
                style = MaterialTheme.typography.bodyMedium

            )
        }
    }
}