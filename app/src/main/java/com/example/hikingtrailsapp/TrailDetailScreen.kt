package com.example.hikingtrailsapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun TrailDetailScreen(trail: Trail, timerViewModel: TimerViewModel){
    Column (modifier = Modifier
        .fillMaxSize()
        .padding(12.dp)
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = trail.name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 12.dp))
        Image(
            painter = rememberAsyncImagePainter(trail.image),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
                .size(220.dp)
                .clip(RoundedCornerShape(24.dp))
        )
        Text(text = trail.shortDesc,
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.bodyMedium)
        TimerScreenContent(timerViewModel = timerViewModel)
    }
}

//@Composable
//fun Timer(){
//    var time by remember {
//        mutableStateOf(0L)
//    }
//
//    var isRunning by remember {
//        mutableStateOf(false)
//    }
//
//    var startTime by remember {
//        mutableStateOf(0L)
//    }
//
//    val context = LocalContext.current
//
//    Column (
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(12.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = formatTime(timeMi = time),
//            style = MaterialTheme.typography.headlineLarge,
//            modifier = Modifier.padding(6.dp)
//        )
//        Row {
//            Button(onClick = {
//                if (isRunning){
//                    isRunning = false
//                } else {
//                    startTime = System.currentTimeMillis() - time
//                    isRunning = true
//                }
//            }) {
//                Text(
//                    text = if (isRunning) "Pause" else "Start", color = Color.White
//                )
//            }
//        }
//    }
//
//    LaunchedEffect(isRunning){
//        while (isRunning){
//            delay(1000)
//            time = System.currentTimeMillis()-startTime
//        }
//    }
//}
//
//@Composable
//fun formatTime(timeMi:Long):String{
//    val hours = TimeUnit.MILLISECONDS.toHours(timeMi)
//    val min = TimeUnit.MILLISECONDS.toMinutes(timeMi)
//    val sec = TimeUnit.MILLISECONDS.toSeconds(timeMi)
//
//    return String.format("%02d:%02d:%02d", hours, min, sec)
//}