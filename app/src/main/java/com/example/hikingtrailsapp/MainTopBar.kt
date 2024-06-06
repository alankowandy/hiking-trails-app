package com.example.hikingtrailsapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.hikingtrailsapp.ui.theme.HikingTrailsAppTheme

@Composable
fun MainTopBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    SearchBarPhoto(
        text = searchTextState,
        searchWidgetState = searchWidgetState,
        onTextChange = onTextChange,
        onCloseClicked = onCloseClicked,
        onSearchClickedIcon = onSearchTriggered,
        onSearchClicked = onSearchClicked
    )
}

@Composable
fun SearchBarPhoto(
    text: String,
    searchWidgetState: SearchWidgetState,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClickedIcon: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp),
        elevation = AppBarDefaults.TopAppBarElevation
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = "https://w.wallhaven.cc/full/rr/wallhaven-rr676j.jpg"),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                when (searchWidgetState) {
                    SearchWidgetState.CLOSED -> {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp, start = 20.dp, end = 20.dp)
                        ) {
                            Text(
                                text = "Górskie Szczytowanie",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier,
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(
                                onClick = { onSearchClickedIcon() },
                                modifier = Modifier
                                    .padding(top = 5.dp)
                                    .background(
                                        color = Color.White,
                                        shape = CircleShape
                                    )
                                    .size(30.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search Icon",
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .fillMaxSize()
                                )
                            }
                        }
                    }

                    SearchWidgetState.OPENED -> {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp, start = 20.dp, end = 20.dp)
                        ) {
                            TextField(
                                value = text,
                                textStyle = TextStyle(Color.White),
                                onValueChange = {
                                    onTextChange(it)
                                },
                                placeholder = {
                                    Text(
                                        text = "Wyszukaj szlak...",
                                        color = Color.White,
                                        modifier = Modifier.alpha(ContentAlpha.medium)
                                    )
                                },
                                singleLine = true,
                                leadingIcon = {
                                    IconButton(
                                        onClick = {},
                                        //modifier = Modifier.alpha(ContentAlpha.medium)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Search,
                                            contentDescription = "Search Icon",
                                            tint = Color.White
                                        )
                                    }
                                },
                                trailingIcon = {
                                    IconButton(
                                        onClick = { onCloseClicked() }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Close Icon",
                                            tint = Color.White
                                        )
                                    }
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Search
                                ),
                                keyboardActions = KeyboardActions(
                                    onSearch = {
                                        onSearchClicked(text)
                                    }
                                ),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.White,
                                    focusedIndicatorColor = Color.White
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }
                }

            }
        }

    }

}