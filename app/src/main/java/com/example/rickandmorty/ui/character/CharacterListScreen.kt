package com.example.rickandmorty.ui.character

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.rickandmorty.ui.components.SearchBar
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
        modifier = Modifier
            .padding(innerPadding)
    ) {
        SearchBar(
            query = query,
            onQueryChange = { viewModel.fetchCharactersByName(sharedViewModel, it) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            else -> {

                LazyColumn(modifier = Modifier.padding(bottom = 16.dp)) {
                    items(characters) { character ->
                        CharacterItem(character)
                    }
                }
            }
        }
    }
}