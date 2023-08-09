package com.sats.bagels.wear.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BagelsRepository {
    private val currentCount = MutableStateFlow(0)

    fun getBagelCount(): Flow<Int> = currentCount.asStateFlow()

    suspend fun increment() {
        currentCount.update { it + 1 }
    }

    suspend fun decrement() {
        currentCount.update { it - 1 }
    }
}
