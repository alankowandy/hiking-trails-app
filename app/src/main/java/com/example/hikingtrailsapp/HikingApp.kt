package com.example.hikingtrailsapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun HikingApp(navController: NavHostController){
    val trailViewModel: MainViewModel = viewModel()
    val timerViewModel: TimerViewModel = viewModel()
    val viewstate by trailViewModel.trailsState
    //val timestate by timerViewModel.timerState

    NavHost(navController = navController, startDestination = Screen.TrailScreen.route){
        composable(route = Screen.TrailScreen.route){
            TrailsScreen(viewstate = viewstate, navigationToTrailDetailScreen = {
                navController.currentBackStackEntry?.savedStateHandle?.set("cat", it)
                navController.navigate(Screen.TrailDetailScreen.route)
            })
        }
        composable(route = Screen.TrailDetailScreen.route){
            val trail = navController.previousBackStackEntry?.savedStateHandle?.
            get<Trail>("cat") ?: Trail("", "", "", "", "")
            TrailDetailScreen(trail = trail, timerViewModel = timerViewModel)
        }
    }
}