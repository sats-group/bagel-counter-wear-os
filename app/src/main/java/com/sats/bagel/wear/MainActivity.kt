package com.sats.bagel.wear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<CounterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.BagelTheme_Main)
        setContent {
            BagelCounterWear(
                isIncrementEnabled = viewModel.isIncrementEnabled,
                isDecrementEnabled = viewModel.isDecrementEnabled,
                increment = viewModel::onIncrementClicked,
                decrement = viewModel::onDecrementClicked,
                count = viewModel.counterValue
            )
        }
    }
}

@Composable
private fun BagelCounterWear(
    isIncrementEnabled: Boolean,
    isDecrementEnabled: Boolean,
    increment: () -> Unit,
    decrement: () -> Unit,
    count: Int,
) {
    val scalingLazyListState = rememberScalingLazyListState()

    MaterialTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            positionIndicator = {
                if (scalingLazyListState.isScrollInProgress) {
                    PositionIndicator(
                        scalingLazyListState =
                        scalingLazyListState
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Image(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(id = R.drawable.ic_bagel),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = count.toString(),
                    textAlign = TextAlign.Center,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Button(
                        modifier = Modifier.size(ButtonDefaults.SmallButtonSize),
                        onClick = increment,
                        enabled = isIncrementEnabled
                    ) {
                        Image(
                            modifier = Modifier.size(28.dp),
                            painter = painterResource(id = R.drawable.ic_chev_up_enabled),
                            contentDescription = null
                        )
                    }
                    Spacer(Modifier.size(20.dp))
                    Button(
                        modifier = Modifier.size(ButtonDefaults.SmallButtonSize),
                        onClick = decrement,
                        enabled = isDecrementEnabled
                    ) {

                        Image(
                            modifier = Modifier.size(28.dp),
                            painter = painterResource(id = R.drawable.ic_chev_down_enabled),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}