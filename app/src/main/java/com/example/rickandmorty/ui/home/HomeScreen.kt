package com.example.rickandmorty.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.rickandmorty.R
import com.example.rickandmorty.data.model.Card
import com.example.rickandmorty.ui.navigation.Routes
import com.example.rickandmorty.viewmodel.CharacterViewModel

@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    viewModel: CharacterViewModel = hiltViewModel(),
    navController: NavController
) {
    val cardInfo = listOf(
        Card(
            "Characters",
            R.drawable.characters,
            Routes.CharacterList.route
        ),
        Card(
            "Worlds",
            R.drawable.locations,
            Routes.Location.route
        ),
        Card(
            "Quiz",
            R.drawable.quiz,
            Routes.Quiz.route
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1B1B1B),
                        Color(0xFF0D0D0D)
                    )
                )
            )
            .padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = rememberAsyncImagePainter(R.drawable.header),
                contentDescription = "Rick and Morty",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(32.dp),
                contentPadding = PaddingValues(12.dp)
            ) {
                items(cardInfo) { card ->
                    CardItem(card, navController)
                }
            }
        }
    }
}

@Composable
fun CardItem(card: Card, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .shadow(8.dp, MaterialTheme.shapes.medium)
            .clickable { navController.navigate(card.route) },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A2A)
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box {
            // Background image with gradient overlay
            Image(
                painter = rememberAsyncImagePainter(card.image),
                contentDescription = card.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0xFF0D0D0D).copy(alpha = 0.8f)
                            )
                        )
                    )
            )

            // Card content
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = card.name,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Color.White,
                        fontSize = 20.sp
                    )
                )
            }
        }
    }
}
