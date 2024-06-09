package com.example.hikingtrailsapp.core.model

import com.example.hikingtrailsapp.core.repository.TrailRepository
import com.example.hikingtrailsapp.core.repository.TrailRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModel {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = "https://sirbdnnsfhcwatijflpz.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InNpcmJkbm5zZmhjd2F0aWpmbHB6Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTU3Nzg4MTYsImV4cCI6MjAzMTM1NDgxNn0.VVyeLULBXZDntLgmDjwNBJd3j4RIhhunYzKa7gO5AdU"
        ) {
            install(Postgrest)
        }
    }

    @Provides
    @Singleton
    fun provideSupabaseDatabase(client: SupabaseClient): Postgrest {
        return client.postgrest
    }

    @Provides
    fun provideTrailRepository(postgrest: Postgrest): TrailRepository {
        return TrailRepositoryImpl(postgrest)
    }
}