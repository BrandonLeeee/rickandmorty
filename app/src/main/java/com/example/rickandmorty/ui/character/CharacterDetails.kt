package com.example.rickandmorty.ui.character

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.rickandmorty.ui.components.LoadingProgress
import com.example.rickandmorty.viewmodel.CharacterViewModel
import com.example.rickandmorty.viewmodel.SharedViewModel

@Composable
fun CharacterDetails(
    innerPadding: PaddingValues,
    navController: NavController,
    characterId: Int,
    viewModel: CharacterViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    val character by viewModel.character.collectAsState()
    val isLoading by sharedViewModel.isLoading.collectAsState()

    // Fetch character when characterId changes
    LaunchedEffect(characterId) {
        viewModel.fetchCharacterById(sharedViewModel, characterId)
    }

    Box(modifier = Modifier.padding(innerPadding)) {
        when {
            isLoading -> {
                LoadingProgress()
            }
            else -> {
                character?.let { char ->
                    LazyColumn {
                        item {
                            Text(text = char.name)
                            Text(text = char.type.ifBlank { "Unknown Type" })
                            Text(text = char.gender)
                        }
                    }

                } ?: Text(text = "Character details not available")
            }
        }
    }
}
