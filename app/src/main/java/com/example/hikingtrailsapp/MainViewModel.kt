package com.example.hikingtrailsapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.io.File

class MainViewModel: ViewModel() {

    private val _trailsState = mutableStateOf(TrailState())
    val trailsState: State<TrailState> = _trailsState

    //SearchBar implementation
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

    val trails: List<Trail> = listOf(
        Trail(
            name = "Beskid Śląski",
            shortDesc = "Skrzyczne jest trochę jak Kasprowy Wierch – też góruje nad popularnym kurortem, też jest rajem dla narciarzy, też można się tam dostać kolejką linową, też jest jednym z najpiękniejszych górskich szczytów, a widoki ze szczytu też zapierają dech w piersiach. Tyle że zamiast Tatr (które majaczą czasem na horyzoncie przy dobrej pogodzie) można podziwiać cały Beskid Śląski, Beskid Żywiecki, Jezioro Żywieckie oraz wiele innych malowniczych zakątków i ciekawych atrakcji turystycznych. Kto nie chce iść na łatwiznę i wjeżdżać na wierzchołek krzesełkiem, ma do dyspozycji dwa szlaki piesze prowadzące z centrum Szczyrku – przez Halę Jaworzyna lub przez przełęcz Becyrek. Na obydwu nie brakuje stromych podejść, ale wrażenia u celu wynagrodzą wszelkie trudy. Tym bardziej, że mieści się tam również schronisko z bogato zaopatrzonym barem.",
            difficulty = "Łatwy",
            image = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Wapienica_%28rzeka%29-zbiornik_Wielka_Laka2.jpg/800px-Wapienica_%28rzeka%29-zbiornik_Wielka_Laka2.jpg",
            time = "3"
        ),
        Trail(
            name = "Beskid Żywiecki",
            shortDesc = "Czeka cię piesza wędrówka plus dawka historii Polski na Wyżynie Krakowsko-Częstochowskiej...",
            difficulty = "Trudny",
            image = "https://ocdn.eu/pulscms-transforms/1/8dSk9kpTURBXy9hZWY3NGYyZTAwZjczYzEwMWNmZDIxOGU3Y2QwYzA5OC5qcGeSlQLNA8AAwsOVAgDNA8DCw94AAaEwBg",
            time = "5"
        ),
        Trail(
            name = "Góry Stołowe",
            shortDesc = "Karkonosze i Góry Izerskie nie imponują może wysokością, ale za to urozmaicony teren, zmieniające się widoki świetnie nadają się na lekką odmianę trekingu...",
            difficulty = "Średni",
            image = "https://ocdn.eu/pulscms-transforms/1/7yAk9kpTURBXy85MzVjN2JhNGFhMjU2NmViZGM1MWNiNDYyYWFiNWZjNC5qcGeSlQLNA8AAwsOVAgDNA8DCw94AAaEwBg",
            time = "4"
        ),
        Trail(
            name = "Góry Świętokrzyskie",
            shortDesc = "Szlak wiedzie przez Góry Świętokrzyskie – najstarsze góry w Polsce, mocno zerodowane, ale za to z dawką historii i natury...",
            difficulty = "Średni",
            image = "https://www.national-geographic.pl/media/cache/default_view/uploads/media/default/0013/55/a69dd9db094179d2ec3202ac622ad7f005a72d66.jpeg",
            time = "7"
        ),
        Trail(
            name = "GŁÓWNY SZLAK BESKIDZKI",
            shortDesc = "Szlak poprowadzono przez sześć pasm górskich: Beskid Śląski, Beskid Żywiecki, Gorce, Beskid Sądecki, Beskid Niski oraz Bieszczady...",
            difficulty = "Trudny",
            image = "https://www.national-geographic.pl/media/cache/default_view/uploads/media/default/0013/55/db921c18679ce3b1e1f22d42616a3720c84daca3.jpeg",
            time = "384.hours"
        ),
        Trail(
            name = "TRZCIEL – PSZCZEW",
            shortDesc = "Trasa wiedzie w dużej części wąskimi ścieżkami nadjeziornymi...",
            difficulty = "Łatwy",
            image = "https://www.national-geographic.pl/media/cache/default_view/uploads/media/default/0013/55/0101ec8c661472eceddd2e862397414c49378ce0.jpeg",
            time = "12"
        )
    )


    init {
        fetchTrails()
    }

    private fun fetchTrails(){
        viewModelScope.launch {
            try {
                _trailsState.value = _trailsState.value.copy(
                    list = trails,
                    loading = false,
                    error = null
                )
            }catch (e: Exception){
                _trailsState.value = _trailsState.value.copy(
                    loading = false,
                    error = "Error fetching trails ${e.message}"
                )
            }
        }
    }

    data class TrailState(
        val loading: Boolean = true,
        val list: List<Trail> = emptyList(),
        val error: String? = null
    )

}

