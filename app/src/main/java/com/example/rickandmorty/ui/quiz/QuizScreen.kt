package com.example.rickandmorty.ui.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.rickandmorty.ui.components.LoadingProgress
import com.example.rickandmorty.ui.navigation.Routes
import com.example.rickandmorty.viewmodel.QuizViewModel
import com.example.rickandmorty.viewmodel.SharedViewModel

@Composable
fun QuizScreen(
    innerPadding: PaddingValues,
    navController: NavController,
    viewModel: QuizViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel(),

    ) {
    val randomQuestion by viewModel.question.collectAsState()
    var selectedAnswer by remember { mutableStateOf("") }
    var isAnswerChecked by remember { mutableStateOf(false) }
    val currentScore by viewModel.currentScore.collectAsState()
    val isLoading by sharedViewModel.isLoading.collectAsState()
    val questionCount by viewModel.questionCount.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchRandomQuestion()
    }

    Column(modifier = Modifier.padding(innerPadding)) {
        if (isLoading) {
            LoadingProgress()
        } else {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Score: $currentScore")
                Text("Question count: $questionCount")
            }
            randomQuestion?.let { question ->
                LazyColumn(modifier = Modifier.padding(12.dp)) {
                    item(randomQuestion) {
                        Text(question.id.toString())
                        Text(
                            text = question.question,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        // Remember the shuffled choices for the current question
                        val shuffledChoices = remember(question) { question.choices.shuffled() }

                        shuffledChoices.forEach { choice ->

                            val backgroundColor = when {
                                isAnswerChecked && choice == question.answer -> Color.Green // Correct answer
                                isAnswerChecked && choice == selectedAnswer && selectedAnswer != question.answer -> Color.Red // Incorrect selected answer
                                else -> Color.Transparent
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                                    .background(backgroundColor)
                                    .selectableGroup()

                            ) {
                                RadioButton(
                                    selected = (selectedAnswer == choice),
                                    onClick = {
                                        // RadioButton click is only enabled if isAnswerChecked is false
                                        if (!isAnswerChecked) {
                                            selectedAnswer = choice
                                        }
                                    }
                                )

                                Text(text = choice)
                            }

                        }

                        if (!isAnswerChecked) {
                            Button(onClick = {
                                if (!isAnswerChecked && selectedAnswer == question.answer) {
                                    viewModel.updateScore()
                                }
                                isAnswerChecked = true

                            }) {
                                Text("Verify Answer")
                            }
                        } else {
                            Button(onClick = {
                                if (questionCount < 10) {
                                    viewModel.fetchRandomQuestion()
                                } else {
                                    navController.navigate(Routes.EndQuiz.route) {
                                        popUpTo("quiz") { inclusive = false }
                                    }
                                }
                                selectedAnswer = ""
                                isAnswerChecked = false
                            }) {
                                Text(text = if (questionCount < 10) "Next question" else "End Quiz")
                            }
                        }
                    }

                }
            }
        }
    }
}
