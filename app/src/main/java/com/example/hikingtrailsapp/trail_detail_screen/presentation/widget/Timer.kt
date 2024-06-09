package com.example.hikingtrailsapp.trail_detail_screen.presentation.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hikingtrailsapp.core.viewmodel.SharedViewModel
import com.example.hikingtrailsapp.trail_detail_screen.presentation.viewmodel.TrailDetailViewModel
import com.example.hikingtrailsapp.core.model.Trail
import com.example.hikingtrailsapp.ui.theme.BlueTheme

@Composable
fun TimerScreenContent(
    timerViewModel: TrailDetailViewModel,
    sharedViewModel: SharedViewModel,
    trail: State<Trail?>
) {
    val time by timerViewModel.formattedTime.collectAsState(initial = "00:00:00")
    val startTime = sharedViewModel.startTime.collectAsState(initial = 0L)

    var showDialog by remember { mutableStateOf(false) }

    if (startTime.value != 0L && (trail.value?.let { sharedViewModel.checkIfRunning(it.id) } == true)) {
        startTime.value?.let { timerViewModel.updateStartTime(it) }
        timerViewModel.startFromTimestamp()
    }

    if (showDialog) {
        TimerDialogBox(
            onDismiss = { showDialog = false },
            onConfirm = {
                showDialog = false
                timerViewModel.start()
                sharedViewModel.updateStartTime(timerViewModel.startTime)
                trail.value?.let { sharedViewModel.updateTimerId(it.id) }
                trail.value?.let { sharedViewModel.updateTrailName(it.name) }
            },
            sharedViewModel = sharedViewModel
        )
    }

    TimerScreen(
        timerValue = time,
        onStartClick = {
            if (startTime.value == 0L) {
                timerViewModel.start()
                sharedViewModel.updateStartTime(timerViewModel.startTime)
                trail.value?.let { sharedViewModel.updateTimerId(it.id) }
                trail.value?.let { sharedViewModel.updateTrailName(it.name) }
            } else if (trail.value?.let { sharedViewModel.checkIfRunning(it.id) } == true) {
                timerViewModel.start()
            } else {
                showDialog = true
            }
        },
        onPauseClick = {
            if (trail.value?.let { sharedViewModel.checkIfRunning(it.id) } == true) {
                timerViewModel.pause()
            }
        },
        onStopClick = {
            if (trail.value?.let { sharedViewModel.checkIfRunning(it.id) } == true) {
                timerViewModel.stop()
                sharedViewModel.updateStartTime(0L)
                sharedViewModel.updateTimerId("")
                sharedViewModel.updateTrailName("")
            }
        }
    )
}

@Composable
fun TimerScreen(
    timerValue: String,
    onStartClick: () -> Unit,
    onPauseClick: () -> Unit,
    onStopClick: () -> Unit
){
    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = timerValue,
            fontSize = 50.sp
        )
        Row {
            Button(
                onClick = onStartClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = BlueTheme
                )
            ) {
                Text(text = "Start")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = onPauseClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = BlueTheme
                )
            ) {
                Text("Pause")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = onStopClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = BlueTheme
                )
            ) {
                Text("Stop")
            }
        }
    }
}