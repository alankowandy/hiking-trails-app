package com.example.hikingtrailsapp

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun HikingApp(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
){
    NavHost(navController = navController, startDestination = TrailScreen.route) {
        composable(TrailScreen.route) {
            TrailsScreenView(
                navController = navController
            )
        }

        composable(
            route = "${TrailDetailScreen.route}/{${TrailDetailScreen.trailId}}",
            arguments = TrailDetailScreen.argument
        ) { navBackStackEntry ->
            val trailId =
                navBackStackEntry.arguments?.getString(TrailDetailScreen.trailId)
            TrailDetailScreenView(
                navController = navController,
                trailId = trailId,
                sharedViewModel = sharedViewModel
            )
        }
    }
}