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
fun HikingApp(navController: NavHostController,
              //modifier: PaddingValues
){
    val trailViewModel: MainViewModel = hiltViewModel()
    val timerViewModel: TimerViewModel = viewModel()
    //val viewstate by trailViewModel.trailsState
    //val timestate by timerViewModel.timerState

//    NavHost(navController = navController, startDestination = Screen.TrailScreen.route){
//        composable(route = Screen.TrailScreen.route){
//            TrailsScreenView(mainViewModel = trailViewModel, navigationToTrailDetailScreen = {
//                navController.currentBackStackEntry?.savedStateHandle?.set("cat", it)
//                navController.navigate(Screen.TrailDetailScreen.route)
//            })
//        }
//        composable(route = Screen.TrailDetailScreen.route){
//            val trail = navController.previousBackStackEntry?.savedStateHandle?.
//            get<Trail>("cat") ?: Trail("", "", "", "", "","")
//            val onBack: () -> Unit = {
//                Log.d("msg", "zapisalem")
//            }
//            TrailDetailScreenView(trail = trail, timerViewModel = timerViewModel, onBack = onBack)
//        }
//    }

    NavHost(navController = navController, startDestination = TrailScreen.route) {
        composable(TrailScreen.route) {
            TrailsScreenView(
                navController = navController
            )
        }

        composable(
            route = "${TrailDetailScreen.route}/{${TrailDetailScreen.argument}}",
            arguments = TrailDetailScreen.argument
        ) {
            navBackStackEntry ->
            val trailId =
                navBackStackEntry.arguments?.getString(TrailDetailScreen.trailId)

        }
    }
}