package com.example.rickandmorty.ui.navigation


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rickandmorty.ui.character.CharacterDetails
import com.example.rickandmorty.ui.character.CharacterListScreen
import com.example.rickandmorty.ui.home.HomeScreen
import com.example.rickandmorty.ui.location.LocationListScreen

@Composable
fun AppNavigation(innerPadding: PaddingValues) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.HomeScreen.route) {

        composable(Routes.HomeScreen.route) {
            HomeScreen(innerPadding, navController = navController)
        }

        composable(Routes.CharacterList.route) {
            CharacterListScreen(innerPadding, navController = navController)
        }

        composable(
            "${Routes.CharacterDetails.route}?{characterId}",
            arguments = listOf(
                navArgument("characterId") {
                    type = NavType.IntType
                })
        ) { backStackEntry ->

            val characterId = backStackEntry.arguments?.getInt("characterId")

            if (characterId != null) {
                CharacterDetails(
                    innerPadding,
                    navController = navController,
                    characterId = characterId
                )
            }
        }

        composable(Routes.Location.route) {
            LocationListScreen(innerPadding, navController = navController)
        }
    }
}
