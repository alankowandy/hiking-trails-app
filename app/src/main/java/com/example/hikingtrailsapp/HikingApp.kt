package com.example.hikingtrailsapp

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

//@Composable
//fun Main(){
//
//    val scaffoldState: ScaffoldState = rememberScaffoldState()
//    val scope: CoroutineScope = rememberCoroutineScope()
//    val navController = rememberNavController()
//    val context = LocalContext.current
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.StartActivityForResult(),
//        onResult = {}
//    )
//    val title = "PolSKarpaty"
//
//    Scaffold(
//        topBar = { AppBarView(title = "PolSKarpaty") },
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = {
//                    sendMessage(context, launcher, navController)
//                          },
//                modifier = Modifier.padding(20.dp),
//                contentColor = Color.White,
//                backgroundColor = Color.Yellow
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.message),
//                    contentDescription = "Message"
//                )
//            }
//        }
//    ) {
//        HikingApp(navController = navController, modifier = it)
//    }
//}

private fun sendMessage(context: Context, launcher: ActivityResultLauncher<Intent>, navController: NavHostController) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, "Hello from the !")
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    launcher.launch(Intent.createChooser(intent, "Send message"))
}

@Composable
fun HikingApp(navController: NavHostController,
              //modifier: PaddingValues
){
    val trailViewModel: MainViewModel = viewModel()
    val timerViewModel: TimerViewModel = viewModel()
    val viewstate by trailViewModel.trailsState
    //val timestate by timerViewModel.timerState

    NavHost(navController = navController, startDestination = Screen.TrailScreen.route){
        composable(route = Screen.TrailScreen.route){
            TrailsScreenView(viewstate = viewstate, navigationToTrailDetailScreen = {
                navController.currentBackStackEntry?.savedStateHandle?.set("cat", it)
                navController.navigate(Screen.TrailDetailScreen.route)
            })
        }
        composable(route = Screen.TrailDetailScreen.route){
            val trail = navController.previousBackStackEntry?.savedStateHandle?.
            get<Trail>("cat") ?: Trail("", "", "", "", "")
            val onBack: () -> Unit = {
                Log.d("msg", "zapisalem")
            }
            TrailDetailScreenView(trail = trail, timerViewModel = timerViewModel, onBack = onBack)
        }
    }
}