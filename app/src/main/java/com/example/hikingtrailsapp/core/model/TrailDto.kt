package com.example.hikingtrailsapp.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrailDto(
    @SerialName("name")
    val name: String,

    @SerialName("shortDesc")
    val shortDesc: String,

    @SerialName("difficulty")
    val difficulty: String,

    @SerialName("image")
    val image: String,

    @SerialName("time")
    val time: String,

    @SerialName("id")
    val id: Int,
)
