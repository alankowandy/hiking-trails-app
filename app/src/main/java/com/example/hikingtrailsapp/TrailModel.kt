package com.example.hikingtrailsapp

import android.content.res.AssetManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import android.content.Context
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader

//data class TrailModel(var time: Long)

class TrailModel {
    object JsonReader {
        fun readJsonFileFromAssets(context: Context, fileName: String): List<Trail>? {
            try {
                // Open the JSON file from assets
                val inputStream = context.assets.open(fileName)

                // Read the JSON data into a string
                val reader = BufferedReader(InputStreamReader(inputStream))
                val jsonString = reader.use { it.readText() }

                // Parse JSON using Gson into a list of objects
                val trailListType = object : TypeToken<List<Trail>>() {}.type
                val trails: List<Trail> = Gson().fromJson(jsonString, trailListType)
                return trails

            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }
    }

//    val jsonString = File("C:\\Users\\Alan\\AndroidStudioProjects\\HikingTrailsApp\\app\\src\\main\\res\\raw\\hikingtrails.json").readText()
//
//    // Use Gson to parse the JSON string into a list of Trail objects
//    val trailListType = object : TypeToken<List<Trail>>() {}.type
//    val trails: List<Trail> = Gson().fromJson(jsonString, trailListType)
}