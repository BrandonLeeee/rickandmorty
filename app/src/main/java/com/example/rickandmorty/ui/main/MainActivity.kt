package com.example.rickandmorty.ui.main

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.rickandmorty.R
import com.example.rickandmorty.ui.navigation.AppNavigation
import com.example.rickandmorty.ui.theme.RickAndMortyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(R.color.customBlack)
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->
                    AppNavigation(innerPadding)
                }
            }
        }
    }
}

fun Activity.enableEdgeToEdge(statusBarColor: Int = android.R.color.white) {
    window.statusBarColor = resources.getColor(statusBarColor, theme)
}