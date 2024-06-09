package com.example.hikingtrailsapp.trail_detail_screen.presentation.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.hikingtrailsapp.R
import com.example.hikingtrailsapp.core.model.Trail

@Composable
fun DetailPhotoBar(
    navController: NavController,
    trailDetails: State<Trail?>
) {
    Surface(
        shape = RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = trailDetails.value?.image
                ),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                IconButton(
                    onClick = { navController.navigateUp() },
                    modifier = Modifier
                        .padding(top = 42.dp, start = 18.dp)
                        //.heightIn(min = 24.dp)
                        .background(
                            color = Color.White,
                            shape = CircleShape
                        )
                        .size(30.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "",
                        tint = Color.Black
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp, start = 20.dp, end = 20.dp)
                ) {
                    trailDetails.value?.let {
                        Text(
                            text = it.name,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 25.dp, start = 20.dp, end = 20.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(70.dp)
                            )
                            .size(width = 90.dp, height = 23.dp)
                    ) {
                        Row {
                            when (trailDetails.value?.difficulty) {
                                "Łatwy" ->
                                    Image(
                                        painterResource(id = R.drawable.flag_green),
                                        contentDescription = ""
                                    )

                                "Średni" ->
                                    Image(
                                        painterResource(id = R.drawable.flag_yellow),
                                        contentDescription = ""
                                    )

                                "Trudny" ->
                                    Image(
                                        painterResource(id = R.drawable.flag_red),
                                        contentDescription = ""
                                    )
                            }
                            Spacer(modifier = Modifier.width(3.dp))
                            trailDetails.value?.let {
                                Text(
                                    text = it.difficulty,
                                    color = Color.Black,
                                    maxLines = 1,
                                    fontSize = 15.sp,
                                    modifier = Modifier
                                        .padding(top = 1.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(70.dp)
                            )
                            .size(width = 90.dp, height = 23.dp)
                    ) {
                        Row {
                            Image(
                                painterResource(id = R.drawable.timer),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(22.dp)
                                    .padding(top = 1.dp, start = 1.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "${trailDetails.value?.time}:00:00",
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 15.sp,
                                modifier = Modifier
                                    .padding(top = 1.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}