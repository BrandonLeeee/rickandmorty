package com.example.rickandmorty.ui.character

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.rickandmorty.R
import com.example.rickandmorty.ui.components.LoadingProgress
import com.example.rickandmorty.ui.components.SearchComponent
import com.example.rickandmorty.ui.navigation.Routes
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
    val page by viewModel.currentPage.collectAsState()
    val query by viewModel.query.collectAsState()
    val info by viewModel.info.collectAsState()
    var isNavigating by remember { mutableStateOf(false) }

    LaunchedEffect(page) {
        viewModel.fetchCharacters(sharedViewModel)
    }
    Column(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1B1B1B),
                        Color(0xFF0D0D0D)
                    )
                )
            )
            .padding(innerPadding)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    onClick = { viewModel.prevPage() },
                    enabled = info?.prev != null
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Back Icon",
                        tint = if (info?.prev == null) Color.Gray else Color(0xFF16ACC9),
                        modifier = Modifier.size(48.dp)

                    )
                }
                Text(
                    text = "Characters",
                    style = TextStyle(
                        fontFamily = FontFamily(
                            Font(R.font.get_schwifty, FontWeight.Normal)
                        ),
                        fontSize = 65.sp,
                        color = Color(0xFF16ACC9),
                        shadow = Shadow(
                            color = Color(0xFFD2DA4B),
                            offset = Offset(6f, 6f),
                            blurRadius = 6f
                        )
                    ),
                    textAlign = TextAlign.Center
                )

                IconButton(
                    onClick = { viewModel.nextPage() },
                    enabled = info?.next != null
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Forward Icon",
                        tint = if (info?.next == null) Color.Gray else Color(0xFF16ACC9),
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

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
                            CharacterItem(
                                character,
                                onDetailsClick = {
                                    if (!isNavigating) {
                                        isNavigating = true
                                        navController.navigate("${Routes.CharacterDetails.route}?${character.id}")
                                    }
                                })
                        }
                    }
                }
            }
        }
    }
}