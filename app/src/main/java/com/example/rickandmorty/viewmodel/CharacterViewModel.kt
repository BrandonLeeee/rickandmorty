package com.example.rickandmorty.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.data.model.Info
import com.example.rickandmorty.repository.CharacterRepository
import com.example.rickandmorty.ui.util.SharedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository,
    private val sharedState: SharedState
) : ViewModel() {


    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    private val _character = MutableStateFlow<Character?>(null)
    val character: StateFlow<Character?> = _character

    private val _info = MutableStateFlow<Info?>(null)
    val info: StateFlow<Info?> = _info

    private val initialPage = "https://rickandmortyapi.com/api/character"


    fun fetchCharacters() {
        viewModelScope.launch {
            sharedState.setLoading(true)
            try {
                val response =
                    repository.getCharacters(sharedState.currentPage.value.ifBlank { initialPage })
                _characters.value = response.results
                _info.value = response.info
                sharedState.nextUrl.value = response.info.next
                sharedState.prevUrl.value = response.info.prev
            } catch (e: HttpException) {
                Log.e("CharacterViewModel", "HTTP error: ${e.code()} - ${e.message()}")
            } catch (e: IOException) {
                Log.e("CharacterViewModel", "Network error", e)
            } catch (e: Exception) {
                Log.e("CharacterViewModel", "Unexpected error", e)
            } finally {
                sharedState.setLoading(false)
            }
        }
    }

    fun fetchCharacterById(characterId: Int) {
        viewModelScope.launch {
            sharedState.setLoading(true)
            try {
                val character = repository.getCharacterById(characterId)
                _character.value = character
            } catch (e: HttpException) {
                Log.e("CharacterViewModel", "HTTP error: ${e.code()} - ${e.message()}")
            } catch (e: IOException) {
                Log.e("CharacterViewModel", "Network error", e)
            } catch (e: Exception) {
                Log.e("CharacterViewModel", "Unexpected error", e)
            } finally {
                sharedState.setLoading(false)
            }
        }
    }

    fun fetchCharactersByName(queryName: String) {
        viewModelScope.launch {
            sharedState.query.value = queryName
            sharedState.setLoading(true)
            try {
                val response = repository.getCharacterByName(queryName)

                // Handle normal case when characters are found
                _characters.value = response.results
                _info.value = response.info
                sharedState.nextUrl.value = response.info.next
                sharedState.prevUrl.value = response.info.prev

            } catch (e: HttpException) {
                if (e.code() == 404) {
                    // Specific handling for 404: No characters found
                    _characters.value = emptyList()
                    _info.value = null
                    Log.w("CharacterViewModel", "No characters found for query: $queryName (404)")
                } else {
                    // Handle other HTTP errors
                    Log.e("CharacterViewModel", "HTTP error: ${e.code()} - ${e.message()}")
                }
            } catch (e: IOException) {
                Log.e("CharacterViewModel", "Network error", e)
            } catch (e: Exception) {
                Log.e("CharacterViewModel", "Unexpected error", e)
            } finally {
                sharedState.setLoading(false)
            }
        }
    }


    fun nextPage() {
        sharedState.nextUrl.value?.let {
            sharedState.currentPage.value = it
        }
    }

    fun prevPage() {
        sharedState.prevUrl.value?.let {
            sharedState.currentPage.value = it
        }
    }
}