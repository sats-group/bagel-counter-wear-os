package com.sats.bagels.wear.features.bagels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sats.bagels.wear.data.BagelsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class BagelsViewModel @Inject constructor() : ViewModel() {
    private val repository = BagelsRepository()

    var uiState by mutableStateOf(BagelsUiState())
        private set

    init {
        viewModelScope.launch {
            repository.getBagelCount().collect { count ->
                uiState = uiState.copy(count = count)
            }
        }
    }

    fun onAddClicked() {
        if (uiState.count >= MaxNumberOfBagels) return

        viewModelScope.launch {
            repository.increment()
        }
    }

    fun onRemoveClicked() {
        if (uiState.count <= 0) return

        viewModelScope.launch {
            repository.decrement()
        }
    }
}

internal const val MaxNumberOfBagels = 8
