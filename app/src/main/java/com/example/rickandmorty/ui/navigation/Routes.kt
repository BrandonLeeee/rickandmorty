package com.example.rickandmorty.ui.navigation

sealed class Routes(val route: String) {
    object HomeScreen : Routes("home")
    object CharacterList : Routes("characterList")
    object CharacterDetails : Routes("characterDetails")
    object Location : Routes("locations")
    object QuizScreen : Routes("quizScreen")
    object StartQuiz : Routes("startQuiz")
    object EndQuiz : Routes("endQuiz")
}