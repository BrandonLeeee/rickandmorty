package com.example.rickandmorty.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rickandmorty.ui.character.CharacterDetails
import com.example.rickandmorty.ui.character.CharacterListScreen
import com.example.rickandmorty.ui.home.HomeScreen
import com.example.rickandmorty.ui.location.LocationListScreen
import com.example.rickandmorty.ui.quiz.EndQuizScreen
import com.example.rickandmorty.ui.quiz.QuizScreen
import com.example.rickandmorty.ui.quiz.StartQuiz
import com.example.rickandmorty.viewmodel.QuizViewModel

@Composable
fun AppNavigation(innerPadding: PaddingValues) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen.route
    ) {
        composable(Routes.HomeScreen.route) {
            HomeScreen(innerPadding, navController = navController)
        }

        composable(Routes.CharacterList.route) {
            CharacterListScreen(innerPadding, navController = navController)
        }

        navigation(startDestination = Routes.StartQuiz.route, route = "quiz") {
            addQuizComposable(
                route = Routes.QuizScreen.route,
                navController = navController
            ) { viewModel ->
                QuizScreen(innerPadding, navController, viewModel)
            }

            addQuizComposable(
                route = Routes.StartQuiz.route,
                navController = navController
            ) { viewModel ->
                StartQuiz(innerPadding, navController, viewModel)
            }

            addQuizComposable(
                route = Routes.EndQuiz.route,
                navController = navController
            ) { viewModel ->
                EndQuizScreen(innerPadding, navController, viewModel)
            }
        }

        composable(
            route = "${Routes.CharacterDetails.route}?{characterId}",
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId")
            characterId?.let {
                CharacterDetails(innerPadding, navController, it)
            }
        }

        composable(Routes.Location.route) {
            LocationListScreen(innerPadding, navController = navController)
        }
    }
}

fun NavGraphBuilder.addQuizComposable(
    route: String,
    navController: NavController,
    content: @Composable (QuizViewModel) -> Unit
) {
    composable(route) {
        val parentEntry = remember(navController.currentBackStackEntry) {
            navController.getBackStackEntry("quiz")
        }
        val viewModel: QuizViewModel = hiltViewModel(parentEntry)
        content(viewModel)
    }
}
