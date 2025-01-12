package com.example.rickandmorty.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.data.model.Info
import com.example.rickandmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {


    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    private val _character = MutableStateFlow<Character?>(null)
    val character: StateFlow<Character?> = _character

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _info = MutableStateFlow<Info?>(null)
    val info: StateFlow<Info?> = _info

    private val _currentPage = MutableStateFlow("https://rickandmortyapi.com/api/character")
    val currentPage: StateFlow<String> = _currentPage

    private val _nextUrl = MutableStateFlow<String?>(null)
    private val _prevUrl = MutableStateFlow<String?>(null)


    fun fetchCharacters(sharedViewModel: SharedViewModel) {
        viewModelScope.launch {
            sharedViewModel.setLoading(true)
            try {
                val response = repository.getCharacters(currentPage.value)
                _characters.value = response.results
                _info.value = response.info
                _nextUrl.value = response.info.next
                _prevUrl.value = response.info.prev
            } catch (e: HttpException) {
                Log.e("CharacterViewModel", "HTTP error: ${e.code()} - ${e.message()}")
            } catch (e: IOException) {
                Log.e("CharacterViewModel", "Network error", e)
            } catch (e: Exception) {
                Log.e("CharacterViewModel", "Unexpected error", e)
            } finally {
                sharedViewModel.setLoading(false)
            }
        }
    }

    fun fetchCharacterById(sharedViewModel: SharedViewModel, characterId: Int) {
        viewModelScope.launch {
            sharedViewModel.setLoading(true)
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
                _info.value = response.info
                _nextUrl.value = response.info.next
                _prevUrl.value = response.info.prev
            } catch (e: HttpException) {
                Log.e("CharacterViewModel", "HTTP error: ${e.code()} - ${e.message()}")
            } catch (e: IOException) {
                Log.e("CharacterViewModel", "Network error", e)
            } catch (e: Exception) {
                Log.e("CharacterViewModel", "Unexpected error", e)
            } finally {
                sharedViewModel.setLoading(false)
            }
        }
    }

    fun nextPage() {
        _info.value?.next.let {
            _currentPage.value = _nextUrl.value ?: ""
        }
    }

    fun prevPage() {
        _info.value?.prev?.let {
            _currentPage.value = _prevUrl.value ?: ""
        }
    }
}