package com.example.rickandmorty.ui.navigation



import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rickandmorty.ui.SplashScreen
import com.example.rickandmorty.ui.home.HomeScreen
import com.example.rickandmorty.ui.character.CharacterListScreen
import com.example.rickandmorty.ui.world.WorldListScreen

@Composable
fun AppNavigation(innerPadding: PaddingValues) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.SplashScreen.route) {
        composable(Routes.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

        composable(Routes.HomeScreen.route) {
            HomeScreen(innerPadding, navController = navController)
        }

        composable(Routes.CharacterList.route) {
            CharacterListScreen(innerPadding, navController = navController)
        }

        composable(Routes.World.route) {
            WorldListScreen(innerPadding, navController = navController)
        }
    }
}
