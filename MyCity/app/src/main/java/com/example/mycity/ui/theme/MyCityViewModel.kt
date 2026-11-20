package com.example.mycity.ui.theme

import androidx.lifecycle.ViewModel
import com.example.mycity.data.Category
import com.example.mycity.data.MyCityDataSource
import com.example.mycity.data.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MyCityViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyCityUiState())
    val uiState: StateFlow<MyCityUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { it.copy(categories = MyCityDataSource.categories) }
    }

    fun selectCategory(category: Category) {
        val filteredPlaces = MyCityDataSource.places.filter { it.categoryId == category.id }
        _uiState.update {
            it.copy(
                selectedCategory = category,
                places = filteredPlaces,
                selectedPlace = null,
                isShowingHomepage = false
            )
        }
    }

    fun selectPlace(place: Place) {
        _uiState.update { it.copy(selectedPlace = place) }
    }

    fun navigateBackToList() {
        _uiState.update { it.copy(selectedPlace = null) }
    }

    fun navigateBackToHome() {
        _uiState.update { it.copy(selectedCategory = null, isShowingHomepage = true) }
    }
}