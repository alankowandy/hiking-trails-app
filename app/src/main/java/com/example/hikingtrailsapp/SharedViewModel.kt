package com.example.hikingtrailsapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SharedViewModel: ViewModel() {

    private val _timerId = MutableStateFlow<String?>("")
    val timerId: Flow<String?> = _timerId

    private val _trailName = MutableStateFlow<String?>("")
    val trailName: Flow<String?> = _trailName

    private val _startTime = MutableStateFlow<Long?>(0L)
    val startTime: Flow<Long?> = _startTime

    fun updateTimerId(trailId: String) {
        _timerId.value = trailId
    }

    fun updateTrailName(trailName: String) {
        _trailName.value = trailName
    }

    fun updateStartTime(startTime: Long) {
        _startTime.value = startTime
    }

    fun checkIfRunning(trailId: String): Boolean {
        return _timerId.value == trailId
    }
}