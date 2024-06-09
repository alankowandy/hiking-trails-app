package com.example.hikingtrailsapp.core.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hikingtrailsapp.core.viewmodel.SharedViewModel
import com.example.hikingtrailsapp.trail_detail_screen.presentation.screen.TrailDetailScreenView
import com.example.hikingtrailsapp.trails_screen.presentation.screen.TrailsScreenView

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