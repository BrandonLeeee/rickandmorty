package com.example.rickandmorty.data.model

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
)

data class Origin(
    val name: String,
    val url: String
)

data class CharacterResponse(
    val info: Info,
    val results: List<Character>
)