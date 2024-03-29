package com.sats.bagels.wear.features.bagels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.composables.ProgressIndicatorSegment
import com.google.android.horologist.composables.SegmentedProgressIndicator

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun BagelsScreen(
    uiState: BagelsUiState,
    onAddClicked: () -> Unit,
    onRemoveClicked: () -> Unit,
) {
    val numberOfBagels = uiState.count
    val startColor = Color(0xFFCE9A32)
    val endColor = Color(0xFF31231D)
    val progress = numberOfBagels / MaxNumberOfBagels.toFloat()

    val trackSegments = List(MaxNumberOfBagels) { index ->
        ProgressIndicatorSegment(1f, lerp(startColor, endColor, index / (MaxNumberOfBagels - 1f)))
    }

    Box(contentAlignment = Alignment.Center) {
        SegmentedProgressIndicator(
            trackSegments = trackSegments,
            progress = progress,
            strokeWidth = 16.dp,
            paddingAngle = 4f,
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("$numberOfBagels", style = MaterialTheme.typography.display1)

            val buttonColors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF2E8B57),
                contentColor = Color.Black,
            )

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onRemoveClicked, colors = buttonColors) {
                    Icon(Icons.Default.Remove, contentDescription = null)
                }

                Button(onAddClicked, colors = buttonColors) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }
        }
    }
}

@Preview(device = "id:wearos_large_round")
@Composable
private fun Preview() {
    Box(Modifier.background(Color.Black)) {
        var uiState by remember { mutableStateOf(BagelsUiState()) }

        BagelsScreen(
            uiState = uiState,
            onAddClicked = { uiState = uiState.copy(count = uiState.count + 1) },
            onRemoveClicked = { uiState = uiState.copy(count = uiState.count - 1) },
        )
    }
}
