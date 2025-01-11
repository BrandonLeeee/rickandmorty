package com.example.rickandmorty.data.model

data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String
)

data class LocationResponse(
    val info: Info,
    val results: List<Location>
)