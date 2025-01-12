package com.example.rickandmorty.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.model.Info
import com.example.rickandmorty.data.model.Location
import com.example.rickandmorty.repository.LocationRepository
import com.example.rickandmorty.ui.util.SharedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val repository: LocationRepository,
    private val sharedState: SharedState
) :
    ViewModel() {

    private val _location = MutableStateFlow<List<Location>>(emptyList())
    val location: StateFlow<List<Location>> = _location

    private val _info = MutableStateFlow<Info?>(null)
    val info: StateFlow<Info?> = _info

    private val initialPage = "https://rickandmortyapi.com/api/location"


    fun fetchLocation() {
        viewModelScope.launch {
            sharedState.setLoading(true)
            try {
                val response =
                    repository.getLocation(sharedState.currentPage.value.ifBlank { initialPage })
                _location.value = response.results
                _info.value = response.info
                sharedState.nextUrl.value = response.info.next
                sharedState.prevUrl.value = response.info.prev
            } catch (e: Exception) {
                Log.e("LocationViewModel", "Error fetching location", e)
            } finally {
                sharedState.setLoading(false)
            }
        }
    }

    fun fetchLocationByName(queryName: String) {
        viewModelScope.launch {
            sharedState.query.value = queryName
            sharedState.setLoading(true)
            try {
                val response = repository.getLocationByName(queryName)
                _location.value = response.results
                _info.value = response.info
                sharedState.nextUrl.value = response.info.next
                sharedState.prevUrl.value = response.info.prev
            } catch (e: HttpException) {
                if (e.code() == 404) {
                    // Specific handling for 404: No characters found
                    _location.value = emptyList()
                    _info.value = null
                    Log.w("LocationViewModel", "No locations found for query: $queryName (404)")
                } else {
                    // Handle other HTTP errors
                    Log.e("LocationViewModel", "HTTP error: ${e.code()} - ${e.message()}")
                }
            } catch (e: Exception) {
                Log.e("LocationViewModel", "Error fetching location", e)
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