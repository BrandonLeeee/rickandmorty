package com.example.rickandmorty.ui.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedState @Inject constructor() {


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    val currentPage = MutableStateFlow("")
    val nextUrl = MutableStateFlow<String?>(null)
    val prevUrl = MutableStateFlow<String?>(null)
    val query = MutableStateFlow("")

    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    fun resetState() {
        _isLoading.value = false
        currentPage.value = ""
        nextUrl.value = null
        prevUrl.value = null
        query.value = ""
    }
}
