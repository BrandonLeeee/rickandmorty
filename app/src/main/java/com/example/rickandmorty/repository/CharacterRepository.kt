package com.example.rickandmorty.repository

import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.data.model.CharacterResponse
import com.example.rickandmorty.data.model.LocationResponse
import com.example.rickandmorty.data.remote.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getCharacters(page: Int): CharacterResponse {
        return apiService.getCharacters(page)
    }

    suspend fun getCharacterByName(name: String): CharacterResponse {
        return apiService.getCharacterByName(name)
    }
}