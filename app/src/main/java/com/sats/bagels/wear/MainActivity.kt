package com.sats.bagels.wear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.sats.bagels.wear.features.bagels.BagelsScreen
import com.sats.bagels.wear.features.bagels.BagelsViewModel
import com.sats.bagels.wear.ui.theme.BagelCounterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        val viewModel: BagelsViewModel = hiltViewModel()

        BagelsScreen(
            uiState = viewModel.uiState,
            onAddClicked = viewModel::onAddClicked,
            onRemoveClicked = viewModel::onRemoveClicked,
        )
    }
}
