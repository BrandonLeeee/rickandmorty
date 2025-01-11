package com.example.rickandmorty.ui.character

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.rickandmorty.ui.components.SearchComponent
import com.example.rickandmorty.ui.components.LoadingProgress
import com.example.rickandmorty.viewmodel.CharacterViewModel
import com.example.rickandmorty.viewmodel.SharedViewModel


@Composable
fun CharacterListScreen(
    innerPadding: PaddingValues,
    viewModel: CharacterViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel(),
    navController: NavController
) {

    val isLoading by sharedViewModel.isLoading.collectAsState()
    val characters by viewModel.characters.collectAsState()
    val page by remember { mutableIntStateOf(1) }
    val query by viewModel.query.collectAsState()

    LaunchedEffect(Unit) { viewModel.fetchCharacters(sharedViewModel, page) }
    Column(
        modifier = Modifier.background(
            Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF1B1B1B),
                    Color(0xFF0D0D0D)
                )
            )
        )
    ) {
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            SearchComponent(
                query = query,
                onQueryChange = { viewModel.fetchCharactersByName(sharedViewModel, it) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            when {
                isLoading -> {
                    LoadingProgress()
                }

                else -> {

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(characters) { character ->
                            CharacterItem(character)
                        }
                    }
                }
            }
        }
    }
}