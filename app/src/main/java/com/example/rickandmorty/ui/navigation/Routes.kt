package com.example.rickandmorty.ui.navigation

sealed class Routes(val route: String) {
    object SplashScreen: Routes("splashScreen")
    object HomeScreen: Routes("home")
    object CharacterList: Routes("characterList")
    object World: Routes("worlds")
    object Episodes: Routes("episodes")
}