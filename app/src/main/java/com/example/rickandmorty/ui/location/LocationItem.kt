package com.example.rickandmorty.ui.location

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.rickandmorty.R
import com.example.rickandmorty.data.model.Location

@Composable
fun LocationItem(location: Location) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .shadow(8.dp, MaterialTheme.shapes.medium),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A2A)
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box {
            // Background image with gradient overlay
            Image(
                painter = rememberAsyncImagePainter(R.drawable.portal),
                contentDescription = location.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentScale = ContentScale.Crop
            )

            // Status label with neon glow
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
                // Glowing status dot
                fun generateColorForType(type: String): Color {
                    val hash = type.hashCode()
                    val r = (hash and 0xFF0000 shr 16) % 256
                    val g = (hash and 0x00FF00 shr 8) % 256
                    val b = (hash and 0x0000FF) % 256
                    return Color(r, g, b)
                }

                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            color = generateColorForType(location.type),
                            shape = CircleShape
                        )
                        .shadow(8.dp, shape = CircleShape) // Glow effect
                )

                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = location.type,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
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
                    .padding(8.dp)
            ) {
                // Character name
                Text(
                    text = location.name,
                    color = Color.White,
                    fontSize = 14.sp

                )
            }
        }
    }
}
