package com.example.hikingtrailsapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay


data class TimerModel(
    var time: Long,
    var isRunning: Boolean
)

class TimerRepository {
    private var _timer = TimerModel(0, false)

    fun getTimer() = _timer

    @Composable
    fun startTimer(){
        _timer.isRunning = true
        countTime()
    }

    fun pauseTimer(){
        _timer.isRunning = false
    }

    fun resetTimer(){
        _timer.isRunning = false
        _timer.time = 0
    }

    @Composable
    fun countTime(){
        LaunchedEffect(_timer.isRunning){
            while (_timer.isRunning){
                delay(1000)
                _timer.time++
            }
        }
    }
}