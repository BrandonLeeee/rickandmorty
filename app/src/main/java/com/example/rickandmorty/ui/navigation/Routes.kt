package com.example.rickandmorty.ui.navigation

sealed class Routes(val route: String) {
    object HomeScreen: Routes("home")
    object CharacterList: Routes("characterList")
    object Worlds: Routes("worlds")
    object Episodes: Routes("episodes")
}