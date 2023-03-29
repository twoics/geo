package com.twoics.geo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.twoics.geo.R
import com.twoics.geo.components.AppBar
import com.twoics.geo.components.BottomBar


//@Preview
@Composable
fun SheetContent(navController: NavController) {
    BoxWithConstraints {
        val boxMaxScopes = this

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            backgroundColor = Color.White
        ) {
            Column {
                Row(
                    Modifier.padding(boxMaxScopes.maxWidth * 0.03f)
                ) {
                    Column(
                        modifier = Modifier.padding(0.dp, 0.dp, boxMaxScopes.maxWidth * 0.03f, 0.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Icon(
                            painter = painterResource(id = com.twoics.geo.R.drawable.arch),
//                        Icons.Filled.AccountBox,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(boxMaxScopes.maxWidth * 0.12f),
                            contentDescription = null
                        )
                    }

                    Column {
                        Text(
                            text = "Театр кукол",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Россия, Красноярск",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }
                    Column(
                        Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        Button(
//                        backgroundColor = Color.White,
                            elevation = ButtonDefaults.elevation(0.dp, 0.dp),

                            onClick = {
                                navController.navigate("bookmarks")
                            }
                        ) {
                            Icon(Icons.Filled.Favorite, contentDescription = null)
                        }
                    }
                }
                Text(
                    modifier = Modifier.padding(boxMaxScopes.maxWidth * 0.03f),
                    text = "Description",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                val scroll = rememberScrollState(0)
                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                            " Morbi ac massa vehicula magna fringilla tempus.Morbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempus..",
                    color = Color.Gray,
                    modifier = Modifier
                        .verticalScroll(scroll)
                        .padding(boxMaxScopes.maxWidth * 0.04f)
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Details(navController: NavController) {
    MaterialTheme {
        BoxWithConstraints {
            val boxScopes = this

            Scaffold(
                topBar = {
                    AppBar("main", navController)
                },
                bottomBar = {
                    BottomBar(navController)
                }

            ) { contentPadding ->
                // Screen content
                Box(modifier = Modifier.padding(contentPadding)) {
                    val sheetState = rememberBottomSheetState(
                        initialValue = BottomSheetValue.Collapsed
                    )
                    val scaffoldState = rememberBottomSheetScaffoldState(
                        bottomSheetState = sheetState
                    )
                    BottomSheetScaffold(
                        scaffoldState = scaffoldState,
                        sheetContent = {
                            SheetContent(navController)
                        },

                        sheetPeekHeight = boxScopes.maxWidth * 0.52f,
                        sheetShape = RoundedCornerShape(
                            boxScopes.maxWidth * 0.05f,
                            boxScopes.maxWidth * 0.05f,
                            0.dp,
                            0.dp
                        ),
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
}
