package com.example.rickandmorty.ui.navigation

sealed class Routes(val route: String) {
    object HomeScreen : Routes("home")
    object CharacterList : Routes("characterList")
    object CharacterDetails : Routes("characterDetails")
    object Location : Routes("locations")
    object Quiz : Routes("quiz")
}