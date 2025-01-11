package com.example.rickandmorty

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Data classes for the response
data class LocationResponse(
    val info: Info,
    val results: List<Location>
)

data class Info(
    val next: String?
)

data class Location(
    val type: String
)

// Retrofit API interface
interface RickAndMortyApi {
    @GET("location")
    suspend fun getLocations(@Query("page") page: Int): LocationResponse
}

// Main function
fun main() = runBlocking {
    val types = mutableSetOf<String>()
    val client = OkHttpClient()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(RickAndMortyApi::class.java)

    var page = 1
    var hasNextPage = true

    withContext(Dispatchers.IO) {
        while (hasNextPage) {
            val response = api.getLocations(page)
            response.results.forEach { location ->
                if (location.type.isNotBlank()) {
                    types.add(location.type)
                }
            }
            hasNextPage = response.info.next != null
            page++
        }
    }

    println("Total unique location types: ${types.size}")
    println("Location types: $types")
}
