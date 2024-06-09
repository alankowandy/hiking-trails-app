package com.example.hikingtrailsapp.core.app

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface Destination {
    val route: String
}

object TrailScreen: Destination {
    override val route = "trails_screen"
}

object TrailDetailScreen: Destination {
    override val route = "trail_detail_screen"
    const val trailId = "trail_id"
    val argument = listOf(navArgument(name = trailId){
        type = NavType.StringType
    })
    fun createRouteWithParam(trailId: String) = "$route/${trailId}"
}