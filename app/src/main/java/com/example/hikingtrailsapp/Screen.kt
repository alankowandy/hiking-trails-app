package com.example.hikingtrailsapp

sealed class Screen(val route:String) {
    object TrailScreen: Screen("TrailScreen")
    object TrailDetailScreen: Screen("TrailDetailScreen")
}