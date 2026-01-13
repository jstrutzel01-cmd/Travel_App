package com.example.travelapp.ui.screens.saved

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
fun SavedScreen(viewModel: SavedViewModel = viewModel(
    factory = SavedViewModelFactory(
        repository = TravelRepository(
            dao = TravelDatabase.getInstance(LocalContext.current).destinationDao()
        )
    )
), modifier: Modifier
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Saved Items")

        when (val state = uiState.value) {
            is SavedUiState.Empty -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No saved destinations")
                }
            }
            is SavedUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is SavedUiState.Success -> {
                if (state.destinations.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text("Nothing to be seen here")
                    }
                } else
                LazyColumn {
                    items(state.destinations) { destination ->
                        DestinationCard(
                            destination = destination,
                            onUnsaveClick = { viewModel.unsaveDestination(it) }
                            )
                    }
                }
            }
            is SavedUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),

                ) {
                    Column {
                        Text("Error")

                        Text(state.message)
                    }
                }
            }
        }
    }

}

@Composable
fun DestinationCard(
    destination: Destination,
    onUnsaveClick: (Destination) -> Unit
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
                IconButton(onClick = { onUnsaveClick(destination) }) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,  // Always filled since it's saved
                        contentDescription = "Remove from saved",
                        tint = MaterialTheme.colorScheme.error  // Red color
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