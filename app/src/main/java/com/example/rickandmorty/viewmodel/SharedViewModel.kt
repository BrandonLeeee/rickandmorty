package com.example.rickandmorty.viewmodel

import androidx.lifecycle.ViewModel
import com.example.rickandmorty.util.SharedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val sharedState: SharedState
) : ViewModel() {

    val currentPage: StateFlow<String> = sharedState.currentPage
    val isLoading: StateFlow<Boolean> = sharedState.isLoading
    val query: StateFlow<String> = sharedState.query

    fun resetState() {
        sharedState.resetState()
    }
}

