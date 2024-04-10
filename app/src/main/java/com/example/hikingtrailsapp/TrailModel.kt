package com.example.hikingtrailsapp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class TrailModelImpl {

    //val jsonLocation = Resources.openRawResource
    val jsonString = File("C:\\Users\\Alan\\AndroidStudioProjects\\HikingTrailsApp\\app\\src\\main\\res\\raw\\hikingtrails.json").readText()

    // Use Gson to parse the JSON string into a list of Trail objects
    val trailListType = object : TypeToken<List<Trail>>() {}.type
    val trails: List<Trail> = Gson().fromJson(jsonString, trailListType)
}

interface TrailModel {
    suspend fun getTrails(): List<Trail>
}

