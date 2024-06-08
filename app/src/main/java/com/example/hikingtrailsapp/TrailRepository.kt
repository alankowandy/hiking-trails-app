package com.example.hikingtrailsapp

import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface TrailRepository {
    suspend fun getTrails(): List<TrailDto>
    suspend fun getTrail(id: String): TrailDto
}

class TrailRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : TrailRepository {
    override suspend fun getTrails(): List<TrailDto> {
        return withContext(Dispatchers.IO) {
            val result = postgrest.from("Trails")
                .select().decodeList<TrailDto>()
            result
        }
    }

    override suspend fun getTrail(id: String): TrailDto {
        return withContext(Dispatchers.IO) {
            postgrest.from("Trails").select {
                filter {
                    eq("id", id)
                }
            }.decodeSingle<TrailDto>()
        }
    }
}