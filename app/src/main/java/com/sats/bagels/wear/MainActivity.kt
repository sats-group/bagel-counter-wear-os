package com.sats.bagels.wear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.sats.bagels.wear.features.bagels.BagelsScreen
import com.sats.bagels.wear.ui.theme.BagelCounterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BagelCounterApp()
        }
    }
}

@Composable
private fun BagelCounterApp() {
    BagelCounterTheme {
        BagelsScreen()
    }
}
