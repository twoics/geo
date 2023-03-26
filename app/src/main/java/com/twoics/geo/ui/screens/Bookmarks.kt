package com.twoics.geo.ui.screens

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
@Preview
private fun BookMarkItem() {
    return Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { },
        elevation = 10.dp,
        shape = RoundedCornerShape(15.dp)
    ) {
        Row(
            Modifier
                .padding(15.dp)
                .height(50.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                Modifier.padding(0.dp, 0.dp, 5.dp, 0.dp)
            ) {
                Icon(
//                    painter = painterResource(id = com.twoics.geo.R.drawable.arch),
                    Icons.Filled.AccountBox,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(28.dp),
                    contentDescription = null
                )
            }

            Column(
                modifier = Modifier.run { width(200.dp) }
            ) {
                Text(
                    text = "Театр Пушкина",
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

@Composable
fun BookmarkList() {
    return LazyColumn(
        modifier = Modifier.fillMaxHeight()
    ) {
        item {
            BookMarkItem()
        }
        item {
            BookMarkItem()
        }

        item {
            BookMarkItem()
        }
        item {
            BookMarkItem()
        }
        item {
            BookMarkItem()
        }

        item {
            BookMarkItem()
        }
        item {
            BookMarkItem()
        }
        item {
            BookMarkItem()
        }

        item {
            BookMarkItem()
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