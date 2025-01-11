package com.example.rickandmorty.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.model.Location
import com.example.rickandmorty.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(private val repository: LocationRepository) :
    ViewModel() {

    private val _location = MutableStateFlow<List<Location>>(emptyList())
    val location: StateFlow<List<Location>> = _location

    fun fetchLocation(sharedViewModel: SharedViewModel, page: Int) {
        viewModelScope.launch {
            sharedViewModel.setLoading(true)
            try {
                val response = repository.getLocation(page)
                _location.value = response.results
            } catch (e: Exception) {
                Log.e("LocationViewModel", "Error fetching location", e)
            } finally {
                sharedViewModel.setLoading(false)
            }
        }
    }
}