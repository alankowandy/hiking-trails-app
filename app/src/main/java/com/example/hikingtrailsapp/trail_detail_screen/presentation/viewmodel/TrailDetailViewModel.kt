package com.example.hikingtrailsapp.trail_detail_screen.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikingtrailsapp.core.app.TrailDetailScreen
import com.example.hikingtrailsapp.core.model.Trail
import com.example.hikingtrailsapp.core.model.TrailDto
import com.example.hikingtrailsapp.core.repository.TrailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrailDetailViewModel @Inject constructor(
    private val trailRepository: TrailRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _trail = MutableStateFlow<Trail?>(null)
    val trail: Flow<Trail?> = _trail

    private val _isLoading = MutableStateFlow(false)
    val isLoading: Flow<Boolean> = _isLoading


    init {
        val id = savedStateHandle.get<String>(TrailDetailScreen.trailId)
        id?.let {
            getTrail(id = it)
        }
    }

    fun getTrail(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val trail = trailRepository.getTrail(id).asDomainModel()
            _trail.emit(trail)
            _isLoading.value = false
        }
    }

    private fun TrailDto.asDomainModel(): Trail {
        return Trail(
            id = this.id.toString(),
            name = this.name,
            shortDesc = this.shortDesc,
            difficulty = this.difficulty,
            image = this.image,
            time = this.time
        )
    }

    // Timer logic implementation
    private val _formattedTime = MutableStateFlow("00:00:00")
    val formattedTime: Flow<String> = _formattedTime

    private val _isRunning = MutableStateFlow(false)
    val isRunning = _isRunning.asStateFlow()

    private var _isActive: Boolean = false
    private var _timeMilis: Long = 0L

    private var _lastTimeStamp: Long = 0L
    private val _startTimestamp = MutableStateFlow(0L)
    val startTimestamp: Flow<Long> = _startTimestamp

    var startTime: Long = 0L

    private var _timerJob: Job? = null

    fun updateStartTime(startTime: Long) {
        _startTimestamp.value = startTime
    }

    fun startFromTimestamp() {
        if (_isActive) return

        _timerJob = viewModelScope.launch {
            _lastTimeStamp = _startTimestamp.value
            _isActive = true
            while (_isActive) {
                delay(1000L)
                _timeMilis += (System.currentTimeMillis() - _lastTimeStamp)
                _lastTimeStamp = System.currentTimeMillis()
                _formattedTime.value = _timeMilis.formatTime()
            }
        }
    }

    fun start() {
        if (_isActive) return

        _timerJob = viewModelScope.launch {
            _lastTimeStamp = System.currentTimeMillis()
            startTime = _lastTimeStamp
            _isActive = true
            while (_isActive) {
                delay(1000L)
                _timeMilis += (System.currentTimeMillis() - _lastTimeStamp).coerceAtMost(1000)
                _lastTimeStamp = System.currentTimeMillis()
                _formattedTime.value = _timeMilis.formatTime()
            }
        }
    }

    fun pause() {
        _timerJob?.cancel()
        _isActive = false
    }

    fun stop() {
        _timerJob?.cancel()
        _isActive = false
        _timeMilis = 0L
        _lastTimeStamp = 0L
        _formattedTime.value = "00:00:00"
    }

    private fun Long.formatTime(): String {
        val totalSeconds = this / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}