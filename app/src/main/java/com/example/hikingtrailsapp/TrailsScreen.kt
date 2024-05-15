package com.example.hikingtrailsapp

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrailsScreenView(
    mainViewModel: MainViewModel,
    viewstate: MainViewModel.TrailState,
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
                },
                onSearchClicked = {
                    Log.d("Searched Text", it)
                },
                onSearchTriggered = {
                    mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            var selectedTabIndex by remember {
                mutableIntStateOf(0)
            }
            val pagerState = rememberPagerState {
                tabItems.size
            }
            LaunchedEffect(selectedTabIndex) {
                pagerState.animateScrollToPage(selectedTabIndex)
            }
            LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
                if (!pagerState.isScrollInProgress) {
                    selectedTabIndex = pagerState.currentPage
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                index ->
                TrailsScreen(
                    viewstate = viewstate,
                    navigationToTrailDetailScreen = navigationToTrailDetailScreen,
                    modifier = it,
                    index = selectedTabIndex
                )
            }
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
}

@Composable
fun TrailsScreen(
    modifier: PaddingValues,
    index: Int,
    viewstate: MainViewModel.TrailState,
    navigationToTrailDetailScreen: (Trail) -> Unit
){
//    val trailViewModel: MainViewModel = viewModel()
//    val viewstate by trailViewModel.trailsState
    Box(modifier = Modifier.fillMaxSize()){
        when{
            viewstate.loading ->{
                //CircularProgressIndicator(modifier.align(Alignment.Center))
            }

            viewstate.error != null ->{
                Text(text = "Error Occured")
            }

            else ->{
                TrailList(trails = viewstate.list, navigationToTrailDetailScreen, index)
            }
        }
    }
}

@Composable
fun TrailList(
    trails: List<Trail>,
    navigationToTrailDetailScreen: (Trail) -> Unit,
    index: Int
){
    LazyVerticalGrid(GridCells.Adaptive(minSize = 130.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, top = 57.dp, end = 8.dp, bottom = 16.dp)
    ){
        items(trails, span = {GridItemSpan(2)}){
            trail ->
            ListItem(trail = trail, navigationToTrailDetailScreen, index)
        }
    }
}

@Composable
fun ListItem(
    trail: Trail,
    navigationToTrailDetailScreen: (Trail) -> Unit,
    index: Int
) {

    when (index) {
        0 -> CardView(trail = trail, navigationToTrailDetailScreen = navigationToTrailDetailScreen)
        1 -> {
            if (trail.difficulty == "Łatwy") {
                CardView(trail = trail, navigationToTrailDetailScreen = navigationToTrailDetailScreen)
            } else {
                null
            }
        }
        2 -> {
            if (trail.difficulty == "Średni") {
                CardView(trail = trail, navigationToTrailDetailScreen = navigationToTrailDetailScreen)
            }
        }
        3 -> {
            if (trail.difficulty == "Trudny") {
                CardView(trail = trail, navigationToTrailDetailScreen = navigationToTrailDetailScreen)
            }
        }
    }

//    ElevatedCard(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(top = 8.dp)
//            .clickable {
//                navigationToTrailDetailScreen(trail)
//            },
//        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
//        shape = MaterialTheme.shapes.large
//    ) {
//        Image(
//            painter = rememberAsyncImagePainter(trail.image),
//            contentDescription = null,
//            contentScale = ContentScale.FillWidth,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(150.dp)
//        )
//        Text(
//            text = trail.name,
//            color = Color.Black,
//            style = MaterialTheme.typography.headlineMedium,
//            fontSize = 20.sp,
//            modifier = Modifier.padding(6.dp)
//        )
//        Spacer(modifier = Modifier.height(10.dp))
//        Row {
//            when (trail.difficulty) {
//                "Łatwy" ->
//                    Image(
//                        painterResource(id = R.drawable.flag_green), "",
//                        modifier = Modifier
//                            .padding(start = 4.dp, bottom = 4.dp)
//                    )
//
//                "Średni" ->
//                    Image(
//                        painterResource(id = R.drawable.flag_yellow), "",
//                        modifier = Modifier
//                            .padding(start = 4.dp, bottom = 4.dp)
//                    )
//
//                "Trudny" ->
//                    Image(
//                        painterResource(id = R.drawable.flag_red), "",
//                        modifier = Modifier
//                            .padding(start = 4.dp, bottom = 4.dp)
//                    )
//            }
//            Text(
//                text = trail.difficulty,
//                style = MaterialTheme.typography.bodyMedium,
//                modifier = Modifier.padding(start = 4.dp, top = 1.dp)
//            )
//            Spacer(modifier = Modifier.weight(1f))
//            Image(painterResource(id = R.drawable.timer), "")
//            Text(
//                text = "${trail.time}:00:00",
//                style = MaterialTheme.typography.bodyMedium,
//                modifier = Modifier.padding(start = 4.dp, top = 1.5.dp, end = 6.dp)
//            )
//        }
//    }
}

@Composable
fun CardView(
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