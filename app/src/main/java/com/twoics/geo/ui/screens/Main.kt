package com.twoics.geo.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twoics.geo.R
import com.twoics.geo.ui.shared.AppBar
import com.twoics.geo.ui.shared.BottomBar

@Composable
private fun FilterItem(
    icon: Painter,
    name: String,
    backgroundColor: Color
) {
    BoxWithConstraints(
        modifier = Modifier
            .shadow(
                elevation = 15.dp,
                shape = RoundedCornerShape(5.dp),
                ambientColor = backgroundColor,
                spotColor = backgroundColor
            )
    ) {
        Column(
            Modifier
                .padding(10.dp, 5.dp)
                .size(60.dp, 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = backgroundColor,
                    contentColor = backgroundColor
                ),
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(15.dp),

                ) {
                Icon(
                    icon,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(42.dp),
                    contentDescription = null
                )
                Text(text = name)
            }

        }
    }
}

@Composable
private fun MainContent() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f),
        backgroundColor = Color.White
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp, 20.dp, 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FilterItem(
                    icon = painterResource(id = R.drawable.arch),
                    name = "Culture",
                    backgroundColor = Color(0xFFFFE9E9)
                )
                FilterItem(
                    icon = painterResource(id = R.drawable.food),
                    name = "Food",
                    backgroundColor = Color(0xFFFFF4E8)
                )
                FilterItem(
                    icon = painterResource(id = R.drawable.nature),
                    name = "Nature",
                    backgroundColor = Color(0xFFEAFFF2)
                )
                FilterItem(
                    icon = painterResource(id = R.drawable.sport),
                    name = "Sport",
                    backgroundColor = Color(0xFFEEF7FF)
                )
            }
            Column(
                modifier = Modifier.padding(20.dp, 0.dp)
            )
            {
                Text(
                    text = "Radius",
                    modifier = Modifier.padding(0.dp, 20.dp, 0.dp, 0.dp),
                )
                var sliderPosition by remember { mutableStateOf(0f) }
                Slider(
                    value = sliderPosition,
                    onValueChange = {
                        sliderPosition = it
                    }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Text(
                        text = "50m",
                        modifier = Modifier.padding(0.dp),
                        color = Color.Gray
                    )
                    Text(
                        text = "2000m",
                        modifier = Modifier.padding(0.dp),
                        color = Color.Gray
                    )
                }
            }

            ExtendedFloatingActionButton(
                onClick = { /* ... */ },
                icon = {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search"
                    )
                },
                text = { Text("Search") }
            )
        }


    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun Main() {
    MaterialTheme {
        Scaffold(
            topBar = {
                AppBar()
            },
            bottomBar = {
                BottomBar()
            }

        ) { contentPadding ->
            // Screen content
            Box(modifier = Modifier.padding(contentPadding)) {
                val sheetState = rememberBottomSheetState(
                    initialValue = BottomSheetValue.Expanded
                )
                val scaffoldState = rememberBottomSheetScaffoldState(
                    bottomSheetState = sheetState
                )
                BottomSheetScaffold(
                    scaffoldState = scaffoldState,
                    sheetContent = {
                        MainContent()
                    },
                    sheetPeekHeight = 100.dp,
                    sheetShape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        Image(
                            painterResource(R.drawable.backdata),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
