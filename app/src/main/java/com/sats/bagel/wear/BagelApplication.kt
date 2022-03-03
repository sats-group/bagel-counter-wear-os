package com.sats.bagel.wear

import android.app.Application

class BagelApplication: Application() {
    val repository by lazy {
        BagelRepositoryImpl.getInstance()
    }
}