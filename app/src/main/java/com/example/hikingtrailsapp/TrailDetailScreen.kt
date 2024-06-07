package com.example.hikingtrailsapp

import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.hikingtrailsapp.ui.theme.BlueTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrailDetailScreenView(
    detailViewModel: TrailDetailViewModel = hiltViewModel(),
    navController: NavController,
    trailId: String?
) {
    val trailDetails = detailViewModel.trail.collectAsState(initial = null)

    // Context and launcher for sending message logic
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {}
    )

    // Tab row names
    val tabItems = listOf(
        "Opis",
        "Czasomierz"
    )
    // Tab row variable to indicate if it's selected
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val pagerState = rememberPagerState {
        tabItems.size
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }

    Scaffold(
        topBar = { DetailPhotoBar(
            navController = navController,
            trailDetails = trailDetails
        ) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    sendMessage(
                        context = context,
                        launcher = launcher,
                        trail = trailDetails
                    )
                },
                modifier = Modifier.padding(20.dp),
                contentColor = Color.White,
                containerColor = BlueTheme
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.message),
                    contentDescription = "message",
                    tint = Color.White
                )
            }
        }
    ) {
        Column {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                contentColor = BlueTheme,
                indicator = {
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(it[selectedTabIndex]),
                        color = BlueTheme
                    )
                },
                divider = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 227.dp)
            ) {
                tabItems.forEachIndexed { index, tabItem ->
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                            selectedTabIndex = index
                        },
                        text = {
                            Text(text = tabItem)
                        }
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
            ) {selectedTabIndex ->
                when (selectedTabIndex){
                    0 -> {
                        TrailDetailScreen(
                            trail = trailDetails,
                            modifier = it
                        )
                    }
                    1 -> {
                        TimerScreenContent(
                            timerViewModel = detailViewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TrailDetailScreen(
    trail: State<Trail?>,
    modifier: PaddingValues
){
//    val currentBackStackEntry = null
//    val navBackStackEntry: NavBackStackEntry? = currentBackStackEntry
//
//    val key = remember {navBackStackEntry}
//
//    DisposableEffect(key1 = key) {
//        onDispose {
//            onBack()
//        }
//    }

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(top = 10.dp, start = 15.dp, end = 15.dp)
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        trail.value?.let {
            Text(
                text = it.shortDesc,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

private fun sendMessage(
    context: Context,
    launcher: ActivityResultLauncher<Intent>,
    trail: State<Trail?>
) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, "Pozdrawiam ze szlaku ${trail.value?.name}!")
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    launcher.launch(Intent.createChooser(intent, "Send message"))
}