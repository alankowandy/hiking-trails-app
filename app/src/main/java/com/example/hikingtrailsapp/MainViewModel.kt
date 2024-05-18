package com.example.hikingtrailsapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val trailRepository: TrailRepository
): ViewModel() {

    // SearchBar implementation
    private val _searchText = MutableStateFlow("")
    val searchText: Flow<String> = _searchText

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    // Trail list implementation
    private val _trailList = MutableStateFlow<List<Trail>?>(listOf())
    val trailList: Flow<List<Trail>?> = _trailList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: Flow<Boolean> = _isLoading

    private val _trails = MutableStateFlow<List<Trail>?>(listOf())
    val trails: Flow<List<Trail>?> = _trails

    private val _trailsEasy = MutableStateFlow<List<Trail>?>(listOf())
    val trailsEasy: Flow<List<Trail>?> = _trailsEasy

    private val _trailsMedium = MutableStateFlow<List<Trail>?>(listOf())
    val trailsMedium: Flow<List<Trail>?> = _trailsMedium

    private val _trailsHard = MutableStateFlow<List<Trail>?>(listOf())
    val trailsHard: Flow<List<Trail>?> = _trailsHard

    private val _trailsFiltered = MutableStateFlow<List<Trail>?>(listOf())
    val trailsFiltered: Flow<List<Trail>?> = _trailsFiltered

    fun updateTrails(trailList: List<Trail>?) {
        _trailsFiltered.value = trailList
    }

    fun filterTrails() {
        _trails.value = _trailList.value
    }

    fun searchTrails() {
        _trails.value = _trails.value?.filter {
            it.doesMatchSearchQuery(_searchTextState.value)
        }
    }

    fun filterTrailEasy() {
        _trailsEasy.value = _trailList.value?.filter {
            it.trailDifficultyQuery("Łatwy")
        }
    }

    fun searchTrailEasy() {
        _trailsEasy.value = _trailsEasy.value?.filter {
            it.doesMatchSearchQuery(_searchTextState.value)
        }
    }

    fun filterTrailMedium() {
        _trailsMedium.value = _trailList.value?.filter {
            it.trailDifficultyQuery("Średni")
        }
    }

    fun searchTrailMedium() {
        _trailsMedium.value = _trailsMedium.value?.filter {
            it.doesMatchSearchQuery(_searchTextState.value)
        }
    }

    fun filterTrailHard() {
        _trailsHard.value = _trailList.value?.filter {
            it.trailDifficultyQuery("Trudny")
        }
    }

    fun searchTrailHard() {
        _trailsHard.value = _trailsHard.value?.filter {
            it.doesMatchSearchQuery(_searchTextState.value)
        }
    }

    init {
        getTrails()
    }

    fun getTrails() {
        viewModelScope.launch {
            _isLoading.value = true
            val trails = trailRepository.getTrails()
            _trailList.emit(trails.map { it -> it.asDomainModel() })
            _isLoading.value = false
        }
    }

    fun getTrailsByDifficulty(difficulty: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val trails = trailRepository.getTrailsByDifficulty(difficulty)
            _trailList.emit(trails.map { it -> it.asDomainModel() })
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
}

