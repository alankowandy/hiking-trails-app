package com.example.hikingtrailsapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//class TimerViewModel(private val repository: TimerRepository): ViewModel() {
//    private val _time = mutableStateOf(repository.getTimer().time)
//    val time: MutableState<Long> = _time
//
//    @Composable
//    fun start(){
//        repository.startTimer()
//        _time.value = repository.getTimer().time
//    }
//
//    fun pause(){
//        repository.pauseTimer()
//        _time.value = repository.getTimer().time
//    }
//
//    fun stop(){
//        repository.resetTimer()
//        _time.value = repository.getTimer().time
//    }
//}

class TimerViewModel : ViewModel() {
    private val _timer = mutableStateOf(0L)
    val timer = _timer
    var isRunning = false

    private var timerJob: Job? = null

    fun startTimer() {
        timerJob?.cancel()
        isRunning = true
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _timer.value++
            }
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
        isRunning = false
    }

    fun stopTimer() {
        _timer.value = 0
        timerJob?.cancel()
        isRunning = false
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
        isRunning = false
    }
}