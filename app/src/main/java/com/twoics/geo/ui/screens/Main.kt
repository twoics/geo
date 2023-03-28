package com.twoics.geo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twoics.geo.ui.shared.AppBar
import com.twoics.geo.ui.shared.BottomBar

@Composable
private fun FilterItem() {
    Card(
        shape = RoundedCornerShape(15.dp),
        backgroundColor = Color.Green
    ) {
        Column(
            Modifier
                .padding(10.dp, 5.dp),
        ) {
            Icon(
//                            painter = painterResource(id = R.drawable.arch),
                Icons.Filled.AccountBox,
                tint = Color.Unspecified,
                modifier = Modifier.size(48.dp),
                contentDescription = null
            )
            Text(text = "Sport")
        }
    }

}

@Composable
private fun MainContent() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f),
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
                FilterItem()
                FilterItem()
                FilterItem()
                FilterItem()
            }
            var sliderPosition by remember { mutableStateOf(0f) }
            Slider(value = 12f, onValueChange = { sliderPosition = it })

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
                    sheetPeekHeight = 200.dp,
                    sheetShape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
//                        Image(
//                            painterResource(R.drawable.backdata),
//                            contentDescription = null,
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier.fillMaxSize()
//                        )
                    }
                }
            }
        }
    }
}
