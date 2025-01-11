package com.example.rickandmorty.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {


    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query


    fun fetchCharacters(sharedViewModel: SharedViewModel, page: Int) {
        viewModelScope.launch {
            sharedViewModel.setLoading(true)
            try {
                val response = repository.getCharacters(page)
                _characters.value = response.results
            } catch (e: Exception) {
                Log.e("CharacterViewModel", "Error fetching characters", e)
            } finally {
                sharedViewModel.setLoading(false)
            }
        }
    }

    fun fetchCharactersByName(sharedViewModel: SharedViewModel, queryName: String) {
        viewModelScope.launch {
            _query.value = queryName
            sharedViewModel.setLoading(true)
            try {
                val response = repository.getCharacterByName(queryName)
                _characters.value = response.results
            } catch (e: Exception) {
                Log.e("CharacterViewModel", "Error fetching characters", e)
            } finally {
                sharedViewModel.setLoading(false)
            }
        }
    }
}