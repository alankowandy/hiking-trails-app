package com.example.hikingtrailsapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Trail(
    val id: String,
    val name: String,
    val shortDesc: String,
    val difficulty: String,
    val image: String,
    val time: String
):Parcelable