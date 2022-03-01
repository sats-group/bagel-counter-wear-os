package com.sats.bagel.wear

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microsoft.signalr.HubConnectionBuilder
import kotlinx.coroutines.launch

class CounterViewModel: ViewModel() {
    var counterValue: Int by mutableStateOf(0)
        private set

    val isDecrementEnabled: Boolean by derivedStateOf { counterValue > 0 }
    val isIncrementEnabled: Boolean by derivedStateOf { counterValue < 8 }

    private val hubConnection = HubConnectionBuilder.create("https://sats-test-no-bagel.azurewebsites.net/api").build()

    init {
        viewModelScope.launch {
            hubConnection.on("UpdatedBagelCount", { newValue: Int -> counterValue = newValue }, Int::class.java)
            hubConnection.start().blockingAwait()
        }
    }

    fun onIncrementClicked() {
        hubConnection.send("IncrementBagelCount")
    }

    fun onDecrementClicked() {
        hubConnection.send("DecrementBagelCount")
    }
}