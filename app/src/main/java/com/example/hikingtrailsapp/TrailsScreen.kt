package com.example.hikingtrailsapp

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrailsScreenView(
    mainViewModel: MainViewModel = hiltViewModel(),
    //viewstate: MainViewModel.TrailState,
    navigationToTrailDetailScreen: (Trail) -> Unit
) {
    val tabItems = listOf(
        TabItem(
            title = "Home",
            unselectedIcon = R.drawable.home_black,
            selectedIcon = R.drawable.home_black
        ),
        TabItem(
            title = "Łatwe",
            unselectedIcon = R.drawable.flag_outlined,
            selectedIcon = R.drawable.flag_green
        ),
        TabItem(
            title = "Średnie",
            unselectedIcon = R.drawable.flag_outlined,
            selectedIcon = R.drawable.flag_yellow
        ),
        TabItem(
            title = "Trudne",
            unselectedIcon = R.drawable.flag_outlined,
            selectedIcon = R.drawable.flag_red
        )
    )
    val searchWidgetState by mainViewModel.searchWidgetState
    val searchTextState by mainViewModel.searchTextState

    val trailList = mainViewModel.trailList.collectAsState(initial = listOf()).value
    val isLoading by mainViewModel.isLoading.collectAsState(initial = false)
    val trails by mainViewModel.trails.collectAsState(initial = listOf())
    val trailsEasy by mainViewModel.trailsEasy.collectAsState(initial = listOf())
    val trailsMedium by mainViewModel.trailsMedium.collectAsState(initial = listOf())
    val trailsHard by mainViewModel.trailsHard.collectAsState(initial = listOf())
    //val trailsFiltered by mainViewModel.trailsFiltered.collectAsState(initial = listOf())

    mainViewModel.filterTrails()
    mainViewModel.filterTrailEasy()
    mainViewModel.filterTrailMedium()
    mainViewModel.filterTrailHard()

    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val pagerState = rememberPagerState {
        tabItems.size
    }

    var trailsFiltered by remember {
        mutableStateOf(trailList)
    }

    val modifier = Modifier

    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }

    Scaffold(
        topBar = { 
            MainTopBar(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = {
                    mainViewModel.updateSearchTextState(newValue = it)
                },
                onCloseClicked = {
                    mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                    mainViewModel.filterTrails()
                    mainViewModel.filterTrailEasy()
                    mainViewModel.filterTrailMedium()
                    mainViewModel.filterTrailHard()
                },
                onSearchClicked = {
                    when(selectedTabIndex) {
                        0 -> { mainViewModel.searchTrails() }
                        1 -> { mainViewModel.searchTrailEasy() }
                        2 -> { mainViewModel.searchTrailMedium() }
                        3 -> { mainViewModel.searchTrailHard() }
                    }
                },
                onSearchTriggered = {
                    mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                TabRow(selectedTabIndex = selectedTabIndex) {
                    tabItems.forEachIndexed { index, tabItem ->
                        Tab(
                            selected = index == selectedTabIndex,
                            onClick = {
                                selectedTabIndex = index
                            },
                            text = {
                                Text(text = tabItem.title)
                            },
                            icon = {
                                if (index == selectedTabIndex) {
                                    Image(
                                        painter = painterResource(id = tabItem.selectedIcon),
                                        contentDescription = ""
                                    )
                                } else {
                                    Image(
                                        painter = painterResource(id = tabItem.unselectedIcon),
                                        contentDescription = ""
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = paddingValues.calculateBottomPadding(), top = 110.dp)
        ) {
            index ->
            trailsFiltered = when (selectedTabIndex) {
                0 -> { trails }
                1 -> { trailsEasy }
                2 -> { trailsMedium }
                3 -> { trailsHard }
                else -> null
            }
            if (!trailsFiltered.isNullOrEmpty() && !isLoading) {
                TrailList(
                    trails = trailsFiltered!!,
                    navigationToTrailDetailScreen = navigationToTrailDetailScreen
                )
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            }
//            TrailsScreen(
//                selectedTabIndex = selectedTabIndex,
//                trailList = trailList,
//                isLoading = isLoading,
//                navigationToTrailDetailScreen = navigationToTrailDetailScreen,
//                trailsFiltered = trailsFiltered
//            )
        }
    }
}

@Composable
fun TrailsScreen(
    selectedTabIndex: Int,
    trailList: List<Trail>?,
    trailsFiltered: List<Trail>?,
    isLoading: Boolean,
    navigationToTrailDetailScreen: (Trail) -> Unit
){


}

@Composable
fun TrailList(
    trails: List<Trail>,
    navigationToTrailDetailScreen: (Trail) -> Unit
){
    LazyVerticalGrid(GridCells.Adaptive(minSize = 130.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, top = 57.dp, end = 8.dp, bottom = 16.dp)
    ){
        items(trails, span = {GridItemSpan(2)}, key = {it.id}){
            trail ->
            ListItem(trail = trail, navigationToTrailDetailScreen)
        }
    }
}

@Composable
fun ListItem(
    trail: Trail,
    navigationToTrailDetailScreen: (Trail) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
            .clickable {
                navigationToTrailDetailScreen(trail)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Image(
            painter = rememberAsyncImagePainter(trail.image),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        Text(
            text = trail.name,
            color = Color.Black,
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 20.sp,
            modifier = Modifier.padding(6.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            when (trail.difficulty) {
                "Łatwy" ->
                    Image(
                        painterResource(id = R.drawable.flag_green), "",
                        modifier = Modifier
                            .padding(start = 4.dp, bottom = 4.dp)
                    )

                "Średni" ->
                    Image(
                        painterResource(id = R.drawable.flag_yellow), "",
                        modifier = Modifier
                            .padding(start = 4.dp, bottom = 4.dp)
                    )

                "Trudny" ->
                    Image(
                        painterResource(id = R.drawable.flag_red), "",
                        modifier = Modifier
                            .padding(start = 4.dp, bottom = 4.dp)
                    )
            }
            Text(
                text = trail.difficulty,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 4.dp, top = 1.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(painterResource(id = R.drawable.timer), "")
            Text(
                text = "${trail.time}:00:00",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 4.dp, top = 1.5.dp, end = 6.dp)
            )
        }
    }
}

data class TabItem(
    val title: String,
    val unselectedIcon: Int,
    val selectedIcon: Int
)