package com.example.rickandmorty.repository

import com.example.rickandmorty.data.model.LocationResponse
import com.example.rickandmorty.data.remote.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getLocation(url: String): LocationResponse {
        return apiService.getLocation(url)
    }

    suspend fun getLocationByName(name: String): LocationResponse {
        return apiService.getLocationByName(name)
    }
}