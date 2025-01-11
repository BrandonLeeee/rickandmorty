package com.example.rickandmorty.data.remote

import com.example.rickandmorty.data.model.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): CharacterResponse

    @GET("character")
    suspend fun getCharacterByName(
        @Query("name") name: String
    ): CharacterResponse
}