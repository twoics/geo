package com.twoics.geo.ui.screens.search

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.twoics.geo.R
import com.twoics.geo.ui.shared.AppBar
import com.twoics.geo.ui.shared.BottomBar

@Composable
private fun FilterItem(
    icon: Painter,
    name: String,
    backgroundColor: Color,
    parentWidth: Dp
) {
    BoxWithConstraints(
        modifier = Modifier
            .shadow(
                elevation = parentWidth * 0.04f,
                shape = RoundedCornerShape(parentWidth * 0.01f),
                ambientColor = backgroundColor,
                spotColor = backgroundColor
            )
    ) {

        Column(
            Modifier
                .padding(parentWidth * 0.03f, parentWidth * 0.01f)
                .size(parentWidth * 0.16f, parentWidth * 0.19f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val selected = remember { mutableStateOf(false) }

            Button(
                onClick = {
                    selected.value = !selected.value
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = backgroundColor,
                    contentColor = backgroundColor
                ),
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(parentWidth * 0.04f),
                border = if (selected.value) BorderStroke(1.dp, Color.Black) else null

            ) {
                Icon(
                    icon,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(parentWidth * 0.1f),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun MainContent(navController: NavController) {
    BoxWithConstraints {
        val boxMaxScopes = this

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .shadow(25.dp),
            backgroundColor = Color.White
        ) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            boxMaxScopes.maxWidth * 0.05f,
                            boxMaxScopes.maxWidth * 0.03f,
                            boxMaxScopes.maxWidth * 0.05f,
                            boxMaxScopes.maxWidth * 0.03f
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FilterItem(
                        icon = painterResource(id = R.drawable.arch),
                        name = "Culture",
                        backgroundColor = Color(0xFFFFE9E9),
                        parentWidth = boxMaxScopes.maxWidth
                    )
                    FilterItem(
                        icon = painterResource(id = R.drawable.food),
                        name = "Food",
                        backgroundColor = Color(0xFFFFF4E8),
                        parentWidth = boxMaxScopes.maxWidth
                    )
                    FilterItem(
                        icon = painterResource(id = R.drawable.nature),
                        name = "Nature",
                        backgroundColor = Color(0xFFEAFFF2),
                        parentWidth = boxMaxScopes.maxWidth
                    )
                    FilterItem(
                        icon = painterResource(id = R.drawable.sport),
                        name = "Sport",
                        backgroundColor = Color(0xFFEEF7FF),
                        parentWidth = boxMaxScopes.maxWidth
                    )
                }
                Column(
                    modifier = Modifier.padding(boxMaxScopes.maxWidth * 0.05f, 0.dp)
                )
                {
                    Text(
                        text = "Radius",
                        modifier = Modifier.padding(0.dp, boxMaxScopes.maxWidth * 0.05f, 0.dp, 0.dp),
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
                    onClick = {
                        navController.navigate("details")
                    },
                    icon = {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "Search"
                        )
                    },
                    backgroundColor = MaterialTheme.colors.primary,
                    text = { Text("Search") }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(navController: NavController) {
    MaterialTheme {
        BoxWithConstraints {
            val boxMaxScopes = this

            Scaffold(
                topBar = {
                    AppBar()
                },
                bottomBar = {
                    BottomBar()
                }

            ) { contentPadding ->
                // Screen content
                Box(
                    modifier = Modifier
                        .padding(contentPadding)
                ) {
                    val sheetState = rememberBottomSheetState(
                        initialValue = BottomSheetValue.Expanded
                    )
                    val scaffoldState = rememberBottomSheetScaffoldState(
                        bottomSheetState = sheetState
                    )
                    BottomSheetScaffold(
                        scaffoldState = scaffoldState,
                        sheetContent = {
                            MainContent(navController)
                        },
                        sheetPeekHeight = boxMaxScopes.maxWidth * 0.3f,
                        sheetShape = RoundedCornerShape(
                            boxMaxScopes.maxWidth * 0.05f,
                            boxMaxScopes.maxWidth * 0.05f,
                            0.dp,
                            0.dp
                        ),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                        ) {
                            Image(
                                painterResource(R.drawable.map),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.9f)
                            )
                        }
                    }
                }
            }
        }
    }
}
