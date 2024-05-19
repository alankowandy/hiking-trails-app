package com.example.hikingtrailsapp

import androidx.annotation.DrawableRes
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(//val title: String
                    val route: String
) {
    object TrailScreen: Screen("TrailsScreenView")
    object TrailDetailScreen: Screen("TrailDetailScreen")
}

interface Destination {
    val route: String
}

object TrailScreen: Destination {
    override val route = "trails_screen"
}

object TrailDetailScreen: Destination {
    override val route = "details_screen"
    const val trailId = "trail_id"
    val argument = listOf(navArgument(name = trailId){
        type = NavType.StringType
    })
    fun createRouteWithParam(trailId: String) = "$route/${trailId}"
}