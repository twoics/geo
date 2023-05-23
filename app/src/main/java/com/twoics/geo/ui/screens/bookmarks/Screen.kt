package com.twoics.geo.ui.screens.bookmarks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.twoics.geo.data.models.Bookmark
import com.twoics.geo.ui.shared.screen.IBottomBar
import com.twoics.geo.ui.shared.screen.IScreen

class BookmarksScreen(
    private var viewModel: BookmarksViewModel,
    private val bottomBar: IBottomBar
) : IScreen {
    private lateinit var sizes: BookmarksScreenSizes

    @Composable
    override fun Screen(

    ) {
        val bookmarks = viewModel.bookmarks

        MaterialTheme {
            BoxWithConstraints {
                sizes = BookmarksScreenSizes(this.maxWidth)

                Scaffold(
                    topBar = {
                        TopBar(viewModel::onEvent)
                    },
                    bottomBar = {
                        bottomBar.ComposableBottomBar()
                    }
                ) { contentPadding ->
                    // Screen content
                    Box(modifier = Modifier.padding(contentPadding)) {
                        LazyColumn(
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            itemsIndexed(bookmarks) { _, data ->
                                Bookmark(data, viewModel::onEvent)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun TopBar(
        onEvent: (BookmarksEvent) -> Unit
    ) {
        Column {
            TopAppBar(title = {
                Text("Bookmarks")
            }, navigationIcon = {
                IconButton(onClick = {
                    onEvent(BookmarksEvent.BackButtonClick)
                }) {
                    Icon(Icons.Filled.ArrowBack, null)
                }
            },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun Bookmark(
        bookmark: Bookmark,
        onEvent: (BookmarksEvent) -> Unit
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(sizes.cardPadding)
                .clickable {},
            elevation = sizes.cardElevation,
            shape = RoundedCornerShape(sizes.cardCorner),
            onClick = {
                onEvent(BookmarksEvent.DetailClick(bookmark = bookmark))
            }
        ) {
            Row(
                Modifier
                    .padding(sizes.rowPadding)
                    .height(sizes.rowHeight),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                TypeIcon()
                PlaceInfo(
                    bookmark.name,
                    bookmark.country,
                    bookmark.city,
                )
                DeleteButton(
                    bookmark = bookmark,
                    onEvent = onEvent
                )
            }
        }
    }

    @Composable
    private fun DeleteButton(
        bookmark: Bookmark,
        onEvent: (BookmarksEvent) -> Unit
    ) {
        Column(
            Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            FloatingActionButton(
                backgroundColor = Color.White,
                contentColor = Color.Gray,
                elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp),
                onClick = {
                    onEvent(BookmarksEvent.DeleteClick(bookmark = bookmark))
                }
            ) {
                Icon(Icons.Filled.Delete, contentDescription = null)
            }
        }
    }

    @Composable
    private fun PlaceInfo(
        name: String,
        country: String,
        city: String,
    ) {
        Column(
            modifier = Modifier.run { width(sizes.contentWidth) }
        ) {
            Text(
                text = name,
                fontSize = 18.sp,
            )
            Text(
                text = "$city, $country",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }

    @Composable
    private fun TypeIcon(
        // TODO Some Enum
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
    }
}
