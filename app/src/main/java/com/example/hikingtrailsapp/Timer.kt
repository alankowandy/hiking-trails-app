package com.example.hikingtrailsapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hikingtrailsapp.ui.theme.BlueTheme

@Composable
fun TimerScreenContent(
    timerViewModel: TrailDetailViewModel,
    sharedViewModel: SharedViewModel,
    trail: State<Trail?>
) {
    val time by timerViewModel.formattedTime.collectAsState(initial = "00:00:00")
    val startTime = sharedViewModel.startTime.collectAsState(initial = 0L)
    val timerId = sharedViewModel.timerId.collectAsState(initial = "")

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
            //style = MaterialTheme.typography.headlineLarge,
            fontSize = 50.sp
            //fontWeight = FontWeight.Bold
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

fun Long.formatTime(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val remainingSeconds = this % 60
    return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
}