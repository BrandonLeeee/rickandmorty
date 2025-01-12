package com.example.rickandmorty.ui.character

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
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

    LaunchedEffect(characterId) {
        viewModel.fetchCharacterById(characterId)
    }

    Box(
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
        when {
            isLoading -> {
                LoadingProgress()
            }

            else -> {
                character?.let { char ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        item {
                            // Character Image and Name Header
                            CharacterHeader(name = char.name, imageUrl = char.image, status = char.status)

                            Spacer(modifier = Modifier.height(16.dp))

                            // Character Details Card
                            CharacterDetailsCard(
                                species = char.species,
                                gender = char.gender,
                                origin = char.origin.name,
                                location = char.location.name,
                                status = char.status,
                                episodeCount = char.episode.size
                            )
                        }
                    }

                } ?: Text(
                    text = "Character details not available",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun CharacterHeader(name: String, imageUrl: String, status: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .size(300.dp)
                .padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = "$name image",
                modifier = Modifier
                    .size(300.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            StatusBadge(status = status)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
            ),
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

    }
}

@Composable
fun StatusBadge(status: String) {
    val statusColor = when (status.lowercase()) {
        "alive" -> Color.Green
        "dead" -> Color.Red
        else -> Color.Gray
    }

    Row(
        modifier = Modifier
            .padding(8.dp)
            .background(
                color = Color(0x80000000),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(
                    color = (statusColor),
                    shape = CircleShape
                )
                .shadow(8.dp, shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = status.lowercase(),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CharacterDetailsCard(
    species: String,
    gender: String,
    origin: String,
    location: String,
    episodeCount: Int,
    status: String
) {
    Card(
        modifier = Modifier
            .width(300.dp) // Match the width of the image
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DetailRow(label = "Species", value = species)
            DetailRow(label = "Gender", value = gender)
            DetailRow(label = "Origin", value = origin)
            DetailRow(label = "Location", value = location)
            DetailRow(label = "Episodes", value = "$episodeCount")
        }
    }
}


@Composable
fun DetailRow(label: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.Gray,
            fontSize = 18.sp
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 18.sp
        )
    }
}
