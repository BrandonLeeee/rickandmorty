package com.example.rickandmorty.repository

import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.data.model.CharacterResponse
import com.example.rickandmorty.data.remote.CharacterApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(
    private val characterApiService: CharacterApiService
) {
    suspend fun getCharacters(url: String): CharacterResponse {
        return characterApiService.getCharacters(url)
    }

    suspend fun getCharacterByName(name: String): CharacterResponse {
        return characterApiService.getCharacterByName(name)
    }

    suspend fun getCharacterById(characterId: Int): Character {
        return characterApiService.getCharacterById(characterId)
    }

}