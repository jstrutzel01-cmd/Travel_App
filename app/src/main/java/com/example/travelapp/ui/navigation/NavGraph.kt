package com.example.travelapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.travelapp.ui.screens.home.HomeScreen
import com.example.travelapp.ui.screens.saved.SavedScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(modifier = modifier)
        }
        composable(route = Screen.Saved.route) {
            SavedScreen(modifier = modifier)
        }

    }
}


sealed class Screen(val route: String) {
    data object Home: Screen("home")
    data object Saved: Screen("saved")
}