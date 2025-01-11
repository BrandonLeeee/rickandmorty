package com.example.rickandmorty.repository

import com.example.rickandmorty.data.model.LocationResponse
import com.example.rickandmorty.data.remote.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getLocation(page: Int): LocationResponse {
        return apiService.getLocation(page)
    }
}