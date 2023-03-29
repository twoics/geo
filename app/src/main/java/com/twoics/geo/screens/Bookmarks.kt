package com.twoics.geo.screens

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
import com.twoics.geo.components.AppBar
import com.twoics.geo.components.BottomBar


@Composable
@Preview
private fun BookmarkItem() {
    BoxWithConstraints {
        val boxMaxScopes = this
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(boxMaxScopes.maxWidth * 0.04f)
                .clickable { },
            elevation = boxMaxScopes.maxWidth * 0.03f,
            shape = RoundedCornerShape(boxMaxScopes.maxWidth * 0.04f)
        ) {
            Row(
                Modifier
                    .padding(boxMaxScopes.maxWidth * 0.04f)
                    .height(boxMaxScopes.maxWidth * 0.12f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    Modifier.padding(0.dp, 0.dp, boxMaxScopes.maxWidth * 0.02f, 0.dp)
                ) {
                    Icon(
//                    painter = painterResource(id = com.twoics.geo.R.drawable.arch),
                        Icons.Filled.AccountBox,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(boxMaxScopes.maxWidth * 0.07f),
                        contentDescription = null
                    )
                }

                Column(
                    modifier = Modifier.run { width(boxMaxScopes.maxWidth * 0.6f) }
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

@Composable
fun BookmarkList() {
    return LazyColumn(
        modifier = Modifier.fillMaxHeight()
    ) {
        item {
            BookmarkItem()
        }
        item {
            BookmarkItem()
        }

        item {
            BookmarkItem()
        }
        item {
            BookmarkItem()
        }
        item {
            BookmarkItem()
        }

        item {
            BookmarkItem()
        }
        item {
            BookmarkItem()
        }
        item {
            BookmarkItem()
        }

        item {
            BookmarkItem()
        }

    }
}


@Composable
@Preview
fun Bookmarks() {
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
                BookmarkList()
            }
        }
    }
}