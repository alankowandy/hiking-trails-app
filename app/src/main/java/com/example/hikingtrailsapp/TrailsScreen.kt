package com.example.hikingtrailsapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun TrailsScreenView(
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
            unselectedIcon = R.drawable.flag,
            selectedIcon = R.drawable.flag_filled
        ),
        TabItem(
            title = "Średnie",
            unselectedIcon = R.drawable.flag,
            selectedIcon = R.drawable.flag_filled
        ),
        TabItem(
            title = "Trudne",
            unselectedIcon = R.drawable.flag,
            selectedIcon = R.drawable.flag_filled
        )
    )
    Scaffold(
        topBar = { AppBarView(title = "PolSKarpaty") },
        bottomBar = {
            var selectedTabIndex by remember {
                mutableIntStateOf(0)
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
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            var selectedTabIndex by remember {
                mutableIntStateOf(0)
            }
            TrailsScreen(
                viewstate = viewstate,
                navigationToTrailDetailScreen = navigationToTrailDetailScreen,
                modifier = it
            )
            //Spacer(modifier = Modifier.weight(1f))

        }


    }
}

@Composable
fun TrailsScreen(
    modifier: PaddingValues,
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
                TrailList(trails = viewstate.list, navigationToTrailDetailScreen)
            }
        }
    }
}

@Composable
fun TrailList(trails: List<Trail>, navigationToTrailDetailScreen: (Trail) -> Unit){
    LazyVerticalGrid(GridCells.Adaptive(minSize = 150.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, top = 57.dp, end = 8.dp, bottom = 16.dp)
    ){
        items(trails, span = {GridItemSpan(2)}){
            trail ->
            ListItem(trail = trail, navigationToTrailDetailScreen)
        }
    }
}

@Composable
fun ListItem(trail: Trail, navigationToTrailDetailScreen: (Trail) -> Unit) {

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
                        painterResource(id = R.drawable.flag), "",
                        modifier = Modifier
                            .padding(start = 4.dp, bottom = 4.dp)
                            .background(Color.Green, shape = RoundedCornerShape(12.dp))
                    )

                "Średni" ->
                    Image(
                        painterResource(id = R.drawable.flag), "",
                        modifier = Modifier
                            .padding(start = 4.dp, bottom = 4.dp)
                            .background(Color.Yellow, shape = RoundedCornerShape(12.dp))
                    )

                "Trudny" ->
                    Image(
                        painterResource(id = R.drawable.flag), "",
                        modifier = Modifier
                            .padding(start = 4.dp, bottom = 4.dp)
                            .background(Color.Red, shape = RoundedCornerShape(12.dp))
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

@Preview(showBackground = true)
@Composable
fun ListItemPreview(){
    val recipieViewModelPreview: MainViewModel = viewModel()
    val viewstatepreview by recipieViewModelPreview.trailsState
    TrailList(trails = viewstatepreview.list, {})
}