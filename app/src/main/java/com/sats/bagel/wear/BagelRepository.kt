package com.sats.bagel.wear

import com.microsoft.signalr.HubConnectionBuilder
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class BagelRepository {
    private val _bagelCount = Channel<Int>()

    val bagelCount: Flow<Int>
        get() = _bagelCount.receiveAsFlow()

    private val hubConnection =
        HubConnectionBuilder.create("https://sats-test-no-bagel.azurewebsites.net/api").build()

    private val scope = MainScope()

    init {
        hubConnection.on(
            "UpdatedBagelCount",
            { newValue: Int -> scope.launch { _bagelCount.send(newValue) } },
            Int::class.java
        )
        hubConnection.start().blockingAwait()
    }
}