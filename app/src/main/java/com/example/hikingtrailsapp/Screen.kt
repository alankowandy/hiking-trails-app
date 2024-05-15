package com.example.hikingtrailsapp

import androidx.annotation.DrawableRes

sealed class Screen(//val title: String
                    val route: String
) {

//        sealed class DrawerScreen(val dTitle: String, val dRoute: String, @DrawableRes val icon: Int)
//        :Screen(dTitle, dRoute){
//            object Home: DrawerScreen(
//                "Start",
//                "TrailsScreen",
//                R.drawable.home_black
//            )
//
//            object Gallery: DrawerScreen(
//                "Galeria",
//                //"GalleryScreen",
//                "TrailsScreen",
//                R.drawable.gallery
//            )
//        }
    object TrailScreen: Screen("TrailsScreenView")
    object TrailDetailScreen: Screen("TrailDetailScreen")
}