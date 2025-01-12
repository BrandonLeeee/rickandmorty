package com.example.rickandmorty.data.model

data class Episode(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String
)

data class EpisodeResponse(
    val info: Info,
    val results: List<Episode>
)
