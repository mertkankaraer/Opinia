package com.example.opinia.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController


enum class Destination(val route: String) {
    START("start") // bunu güncelle splash screen olacak şekilde (kaan)
    Log.d("Destination", "Route: ${Destination.START.route}")
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destination.START.route, builder = {

        }
    )
}