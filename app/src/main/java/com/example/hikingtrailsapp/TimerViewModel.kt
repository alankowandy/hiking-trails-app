package com.example.hikingtrailsapp

import android.os.SystemClock
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {
    private val _timerState = mutableStateOf(TimerState())
    val timerState: State<TimerState> = _timerState
    private val _time = mutableStateOf(timerState.value.time)
    val time: MutableState<Long> = _time

    //private val _timer = mutableStateOf(0L)
    //val timer = _timer
    //var isRunning = false

    private var timerJob: Job? = null

    fun startTimer() {
        timerJob?.cancel()
        _timerState.value = _timerState.value.copy(
            isRunning = true,
            startTime = SystemClock.uptimeMillis().coerceAtMost(1000)
        )
        timerJob = viewModelScope.launch {
            while (_timerState.value.isRunning) {
                val duration = (SystemClock.uptimeMillis() - _timerState.value.startTime).coerceAtMost(1000)
                _timerState.value = _timerState.value.copy(
                    time = duration
                )
                delay(1000)
            }
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
        _timerState.value = _timerState.value.copy(
            isRunning = false
        )
    }

    fun stopTimer() {
        timerJob?.cancel()
        _timerState.value = _timerState.value.copy(
            isRunning = false,
            startTime = 0,
            time = 0
        )
    }

//    fun startTimer() {
//        timerJob?.cancel()
//        isRunning = true
//        timerJob = viewModelScope.launch {
//            while (true) {
//                delay(1000)
//                _timer.value++
//            }
//        }
//    }

//    fun pauseTimer() {
//        timerJob?.cancel()
//        isRunning = false
//    }

//    fun stopTimer() {
//        _timer.value = 0
//        timerJob?.cancel()
//        isRunning = false
//    }

//    override fun onCleared() {
//        super.onCleared()
//        timerJob?.cancel()
//        isRunning = false
//    }

    data class TimerState(
        val time: Long = 0L,
        val startTime: Long = 0L,
        val isRunning: Boolean = false,
        val trackName: String? = null
    )
}