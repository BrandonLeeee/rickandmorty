package com.example.rickandmorty.ui.navigation

import HomeScreen
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rickandmorty.ui.character.CharacterListScreen
import com.example.rickandmorty.ui.world.WorldScreen

@Composable
fun AppNavigation(innerPadding: PaddingValues) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.HomeScreen.route){
        composable(Routes.HomeScreen.route){
            HomeScreen(innerPadding, navController = navController)
        }

        composable(Routes.CharacterList.route){
            CharacterListScreen(innerPadding, navController = navController)
        }

        composable(Routes.Worlds.route){
            WorldScreen(innerPadding, navController = navController)
        }

        composable(Routes.Worlds.route){
            WorldScreen(innerPadding, navController = navController)
        }

    }


}