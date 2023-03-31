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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.twoics.geo.R
import com.twoics.geo.ui.shared.AppBar
import com.twoics.geo.ui.shared.BottomBar


@Composable
fun FilterButton(
    icon: Painter,
    name: String,
    backgroundColor: Color,
    parentWidth: Dp
) {
    val sizes = SearchScreenConfiguration(parentWidth)
    BoxWithConstraints(
        modifier = Modifier
            .shadow(
                elevation = sizes.filterButtonShadowElevation,
                shape = RoundedCornerShape(sizes.filterButtonShadowCorner),
                ambientColor = backgroundColor,
                spotColor = backgroundColor
            )
    ) {

        Column(
            Modifier
                .padding(sizes.filterButtonHorizontalPadding, sizes.filterButtonVerticalPadding)
                .size(sizes.filterButtonWidth, sizes.filterButtonHeight),
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
                shape = RoundedCornerShape(sizes.filterButtonCorner),
                border = if (selected.value) BorderStroke(1.dp, Color.Black) else null

            ) {
                Icon(
                    icon,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(sizes.filterButtonIcon),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun SheetContent() {
    BoxWithConstraints {
        val sizes = SearchScreenConfiguration(this.maxWidth)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(sizes.sheetMaxHeight)
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
                            sizes.sliderHorizontalPadding,
                            sizes.buttonsVerticalPadding,
                            sizes.sliderHorizontalPadding,
                            sizes.buttonsVerticalPadding
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FilterButton(
                        icon = painterResource(id = R.drawable.arch),
                        name = "Culture",
                        backgroundColor = Color(0xFFFFE9E9),
                        parentWidth = sizes.maxWidth
                    )
                    FilterButton(
                        icon = painterResource(id = R.drawable.food),
                        name = "Food",
                        backgroundColor = Color(0xFFFFF4E8),
                        parentWidth = sizes.maxWidth
                    )
                    FilterButton(
                        icon = painterResource(id = R.drawable.nature),
                        name = "Nature",
                        backgroundColor = Color(0xFFEAFFF2),
                        parentWidth = sizes.maxWidth
                    )
                    FilterButton(
                        icon = painterResource(id = R.drawable.sport),
                        name = "Sport",
                        backgroundColor = Color(0xFFEEF7FF),
                        parentWidth = sizes.maxWidth
                    )
                }
                Column(
                    modifier = Modifier.padding(sizes.sliderHorizontalPadding, 0.dp)
                )
                {
                    Text(
                        text = "Radius",
                        modifier = Modifier.padding(0.dp, sizes.sliderHorizontalPadding, 0.dp, 0.dp),
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
//                        navController.navigate("details")
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
@Preview
fun SearchScreen() {
    MaterialTheme {
        BoxWithConstraints {
            val sizes = SearchScreenConfiguration(this.maxWidth)

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
                            SheetContent()
                        },
                        sheetPeekHeight = sizes.sheetPeakHeight,
                        sheetShape = RoundedCornerShape(
                            sizes.sheetCorner,
                            sizes.sheetCorner,
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
                                    .fillMaxHeight(sizes.backgroundHeight)
                            )
                        }
                    }
                }
            }
        }
    }
}
