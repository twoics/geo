package com.twoics.geo.ui.screens.bookmarks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.twoics.geo.ui.shared.AppBar
import com.twoics.geo.ui.shared.BottomBar

@Composable
private fun Bookmarks() {
    BoxWithConstraints {
        val sizes = BookmarksScreenConfiguration(this.maxWidth)

        LazyColumn(
            modifier = Modifier.fillMaxHeight()
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(sizes.cardPadding)
                        .clickable { },
                    elevation = sizes.cardElevation,
                    shape = RoundedCornerShape(sizes.cardCorner)
                ) {
                    Row(
                        Modifier
                            .padding(sizes.rowPadding)
                            .height(sizes.rowHeight),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            Modifier.padding(0.dp, 0.dp, sizes.iconRightPadding, 0.dp)
                        ) {
                            Icon(
//                    painter = painterResource(id = com.twoics.geo.R.drawable.arch),
                                Icons.Filled.AccountBox,
                                tint = Color.Unspecified,
                                modifier = Modifier.size(sizes.iconSize),
                                contentDescription = null
                            )
                        }

                        Column(
                            modifier = Modifier.run { width(sizes.contentWidth) }
                        ) {
                            Text(
                                text = "Театр кукол",
                                fontSize = 18.sp,
                            )
                            Text(
                                text = "Красноярск, Россия",
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                        Column(
                            Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            FloatingActionButton(
                                backgroundColor = Color.White,
                                contentColor = Color.Gray,
                                elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp),
                                onClick = { }
                            ) {
                                Icon(Icons.Filled.Delete, contentDescription = null)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
@Preview
fun BookmarksScreen() {
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
                Bookmarks()
            }
        }
    }
}