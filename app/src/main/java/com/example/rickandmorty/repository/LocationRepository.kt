package com.example.rickandmorty.repository

import com.example.rickandmorty.data.model.LocationResponse
import com.example.rickandmorty.data.remote.CharacterApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(private val characterApiService: CharacterApiService) {
    suspend fun getLocation(url: String): LocationResponse {
        return characterApiService.getLocation(url)
    }

    suspend fun getLocationByName(name: String): LocationResponse {
        return characterApiService.getLocationByName(name)
    }
}