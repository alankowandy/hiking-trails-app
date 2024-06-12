package com.example.hikingtrailsapp.core.model

data class Trail(
    val id: String,
    val name: String,
    val shortDesc: String,
    val difficulty: String,
    val image: String,
    val time: String
) {
    fun trailDifficultyQuery(query: String): Boolean {
        val matchingDifficulty = listOf(
            difficulty
        )
        return matchingDifficulty.any {
            it.contains(query, true)
        }
    }

    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            name,
            "${name.first()}"
        )
        return matchingCombinations.any {
            it.contains(query, true)
        }
    }
}