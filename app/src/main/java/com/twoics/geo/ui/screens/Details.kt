package com.twoics.geo.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.twoics.geo.R
import com.twoics.geo.ui.shared.AppBar
import com.twoics.geo.ui.shared.BottomBar


//@Preview
@Composable
fun SheetContent() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        backgroundColor = Color.White
    ) {
        Column {
            Row(
                Modifier.padding(10.dp)
            ) {
                Column(
                    modifier = Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Icon(
                    painter = painterResource(id = com.twoics.geo.R.drawable.arch),
//                        Icons.Filled.AccountBox,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(48.dp),
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

                        onClick = { }
                    ) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            }
            Text(
                modifier = Modifier.padding(10.dp),
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
                    .padding(15.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun Details() {
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
                    initialValue = BottomSheetValue.Collapsed
                )
                val scaffoldState = rememberBottomSheetScaffoldState(
                    bottomSheetState = sheetState
                )
                BottomSheetScaffold(
                    scaffoldState = scaffoldState,
                    sheetContent = {
                        SheetContent()
                    },
                    sheetPeekHeight = 200.dp,
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
