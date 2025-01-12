package com.example.rickandmorty.data.remote

import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.data.model.CharacterResponse
import com.example.rickandmorty.data.model.LocationResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getCharacters(
        @Url url: String
    ): CharacterResponse

    @GET("character")
    suspend fun getCharacterByName(
        @Query("name") name: String
    ): CharacterResponse

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): Character

    @GET
    suspend fun getLocation(
        @Url url: String
    ): LocationResponse

    @GET("location")
    suspend fun getLocationByName(
        @Query("name") name: String
    ): LocationResponse
}