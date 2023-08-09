package com.sats.bagels.wear.data

import com.microsoft.signalr.HubConnectionBuilder
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BagelsRepository @Inject constructor() {
    private val bagelCountStateFlow = MutableStateFlow(0)

    private val hubConnection = HubConnectionBuilder
        .create("https://sats-test-no-bagel.azurewebsites.net/api")
        .build()

    init {
        hubConnection.on(
            "UpdatedBagelCount",
            { value -> bagelCountStateFlow.update { value } },
            Int::class.java,
        )

        hubConnection.start().blockingAwait()
    }

    fun getBagelCount(): Flow<Int> = bagelCountStateFlow.asStateFlow()

    fun increment() {
        hubConnection.send("IncrementBagelCount")
    }

    fun decrement() {
        hubConnection.send("DecrementBagelCount")
    }
}
